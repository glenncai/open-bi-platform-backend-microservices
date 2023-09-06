package com.glenncai.openbiplatform.aianalytics.service.impl;

import static com.glenncai.openbiplatform.common.utils.NetUtils.getClientIpAddress;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glenncai.openbiplatform.aianalytics.constant.BiMqConstant;
import com.glenncai.openbiplatform.aianalytics.constant.ChartConstant;
import com.glenncai.openbiplatform.aianalytics.feign.IpFeign;
import com.glenncai.openbiplatform.aianalytics.manager.RedisLimiterManager;
import com.glenncai.openbiplatform.aianalytics.mapper.ChartMapper;
import com.glenncai.openbiplatform.aianalytics.model.dto.ChartGenByAiRequest;
import com.glenncai.openbiplatform.aianalytics.model.dto.ChartUpdateStatusRequest;
import com.glenncai.openbiplatform.aianalytics.model.dto.ChatRequest;
import com.glenncai.openbiplatform.aianalytics.model.entity.Chart;
import com.glenncai.openbiplatform.aianalytics.model.enums.ChartStatusEnum;
import com.glenncai.openbiplatform.aianalytics.mq.BiMessageProducer;
import com.glenncai.openbiplatform.aianalytics.service.ChartService;
import com.glenncai.openbiplatform.common.constant.AiConstant;
import com.glenncai.openbiplatform.common.constant.UserConstant;
import com.glenncai.openbiplatform.common.exception.BusinessException;
import com.glenncai.openbiplatform.common.exception.enums.ChartExceptionEnum;
import com.glenncai.openbiplatform.common.model.dto.CheckQuotaReq;
import com.glenncai.openbiplatform.common.model.dto.ReduceCallQuotaReq;
import com.glenncai.openbiplatform.common.utils.ChartUtils;
import com.glenncai.openbiplatform.common.utils.ExcelUtils;
import com.glenncai.openbiplatform.common.utils.FileUtils;
import com.glenncai.openbiplatform.common.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.StringJoiner;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Chart service implementation
 *
 * @author Glenn Cai
 * @version 1.0 03/09/2023
 */
@Service
@Slf4j
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService {

  @Resource
  private IpFeign ipFeign;

  @Resource
  private RedisLimiterManager redisLimiterManager;

  @Resource
  private BiMessageProducer biMessageProducer;

  /**
   * Generate chart by AI asynchronously (RabbitMQ)
   *
   * @param multipartFile       multipart file
   * @param chartGenByAiRequest chart gen by ai request body
   * @param request             http request
   */
  @Override
  public void genChartByAiAsyncMq(MultipartFile multipartFile,
                                  ChartGenByAiRequest chartGenByAiRequest,
                                  HttpServletRequest request) {
    String clientIp = getClientIpAddress(request);
    String token = JwtUtils.getJwtFromAuthorizationHeader(request);
    String originalFilename = multipartFile.getOriginalFilename();
    String chartName = chartGenByAiRequest.getName();
    String chartType = chartGenByAiRequest.getChartType();
    String goal = chartGenByAiRequest.getGoal();
    ChatRequest chatRequest = new ChatRequest();

    String currentUserRole = JwtUtils.getFilteredPayloads(token).getStr("role");
    long currentUserId = JwtUtils.getFilteredPayloads(token).getLong("id");

    // Rate limit for the current user in this method
    redisLimiterManager.doRateLimit(
        AiConstant.AI_REDIS_RATE_LIMIT_PREFIX + currentUserId);

    // Check AI remaining quota
    CheckQuotaReq checkQuotaReq = new CheckQuotaReq();
    checkQuotaReq.setUserId(currentUserId);
    ipFeign.checkRemainingQuota(checkQuotaReq);

    if (StringUtils.isBlank(goal)) {
      throw new BusinessException(ChartExceptionEnum.CHART_EMPTY_GOAL_ERROR.getCode(),
                                  ChartExceptionEnum.CHART_EMPTY_GOAL_ERROR.getMessage());
    }
    if (FileUtils.isInvalidFileSize(multipartFile, ChartConstant.FILE_MAX_SIZE, "M")) {
      throw new BusinessException(ChartExceptionEnum.CHART_FILE_SIZE_ERROR.getCode(),
                                  ChartExceptionEnum.CHART_FILE_SIZE_ERROR.getMessage());
    }
    if (FileUtils.isInvalidFileExtension(multipartFile.getOriginalFilename(),
                                         ChartConstant.VALID_FILE_EXTENSIONS)) {
      throw new BusinessException(ChartExceptionEnum.CHART_FILE_EXTENSION_ERROR.getCode(),
                                  ChartExceptionEnum.CHART_FILE_EXTENSION_ERROR.getMessage());
    }

    StringJoiner userInput = new StringJoiner("\n");
    userInput.add("Analysis needs:");
    String userGoal = goal;
    if (StringUtils.isNotBlank(chartType)) {
      userGoal += " , please generate a " + chartType + " chart.";
    }
    userInput.add(userGoal);
    userInput.add("Raw data:");

    // Compressed data
    String csvData = ExcelUtils.excelToCsv(multipartFile);
    userInput.add(csvData);

    chatRequest.setKey(AiConstant.AI_API_KEY_VALUE);
    chatRequest.setMessage(userInput.toString());

    // Insert waiting chart record first
    Chart chart = new Chart();
    chartName = ChartUtils.genChartNameAuto(chartName, originalFilename);
    chart.setGoal(userGoal);
    chart.setName(chartName);
    chart.setChartData(csvData);
    chart.setChartType(StringUtils.isBlank(chartType) ? "auto" : chartType);
    chart.setStatus(ChartStatusEnum.WAITING.getValue());
    chart.setExecMessage(userInput.toString());
    chart.setUserId(currentUserId);
    boolean saveChartWaitingResult = this.save(chart);
    if (!saveChartWaitingResult) {
      log.error("Client IP: {}, Error message: Failed to save chart waiting record",
                clientIp);
      throw new BusinessException(ChartExceptionEnum.CHART_SAVE_ERROR.getCode(),
                                  ChartExceptionEnum.CHART_SAVE_ERROR.getMessage());
    }

    // Send message to RabbitMQ
    String routingKey = "";
    if (UserConstant.ADMIN_ROLE.equals(currentUserRole)) {
      routingKey = BiMqConstant.BI_MQ_ADMIN_ROUTING_KEY;
    } else if (UserConstant.DEFAULT_ROLE.equals(currentUserRole)) {
      routingKey = BiMqConstant.BI_MQ_USER_ROUTING_KEY;
    }
    long newChartId = chart.getId();
    JSONObject jsonObject = new JSONObject();
    jsonObject.set(BiMqConstant.BI_MQ_MESSAGE_CHART_ID_KEY, newChartId);
    jsonObject.set(BiMqConstant.BI_MQ_MESSAGE_USER_ID_KEY, currentUserId);
    String message = JSONUtil.toJsonStr(jsonObject);
    biMessageProducer.sendMessage(message, routingKey);

    // Reduce AI remaining quota
    ReduceCallQuotaReq reduceCallQuotaReq = new ReduceCallQuotaReq();
    reduceCallQuotaReq.setUserId(currentUserId);
    ipFeign.reduceCallQuota(reduceCallQuotaReq);
  }

  /**
   * Updates chart status
   *
   * @param chartUpdateStatusRequest chart update status request body
   */
  public void updateChartStatus(ChartUpdateStatusRequest chartUpdateStatusRequest) {
    Chart chart = new Chart();
    chart.setId(chartUpdateStatusRequest.getId());
    chart.setStatus(chartUpdateStatusRequest.getStatus().getValue());
    boolean updateChartStatusResult = this.updateById(chart);
    if (!updateChartStatusResult) {
      log.error("Error message: Failed to update chart status");
      throw new BusinessException(ChartExceptionEnum.CHART_SAVE_ERROR.getCode(),
                                  ChartExceptionEnum.CHART_SAVE_ERROR.getMessage());
    }
  }
}




