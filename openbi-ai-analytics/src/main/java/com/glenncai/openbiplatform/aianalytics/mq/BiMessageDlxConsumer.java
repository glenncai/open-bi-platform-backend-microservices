package com.glenncai.openbiplatform.aianalytics.mq;

import cn.hutool.json.JSONUtil;
import com.glenncai.openbiplatform.aianalytics.constant.BiMqConstant;
import com.glenncai.openbiplatform.aianalytics.constant.ChartConstant;
import com.glenncai.openbiplatform.aianalytics.feign.IpFeign;
import com.glenncai.openbiplatform.aianalytics.mapper.AiManager;
import com.glenncai.openbiplatform.aianalytics.model.dto.ChartUpdateStatusRequest;
import com.glenncai.openbiplatform.aianalytics.model.dto.ChatRequest;
import com.glenncai.openbiplatform.aianalytics.model.entity.Chart;
import com.glenncai.openbiplatform.aianalytics.model.enums.ChartStatusEnum;
import com.glenncai.openbiplatform.aianalytics.service.ChartService;
import com.glenncai.openbiplatform.common.constant.AiConstant;
import com.glenncai.openbiplatform.common.exception.BusinessException;
import com.glenncai.openbiplatform.common.exception.enums.AiExceptionEnum;
import com.glenncai.openbiplatform.common.exception.enums.MqExceptionEnum;
import com.glenncai.openbiplatform.common.model.dto.IncreaseCallQuotaReq;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import javax.annotation.Resource;

/**
 * BI RabbitMQ dead letter queue consumer
 *
 * @author Glenn Cai
 * @version 1.0 30/08/2023
 */
@Component
@Slf4j
public class BiMessageDlxConsumer {

  @Resource
  private ChartService chartService;

  @Resource
  private IpFeign ipFeign;

  @Resource
  private AiManager aiManager;

  @RabbitListener(queues = {BiMqConstant.BI_DLX_USER_QUEUE_NAME,
      BiMqConstant.BI_DLX_ADMIN_QUEUE_NAME},
      ackMode = "MANUAL")
  public void receiveMessage(String message, Channel channel,
                             @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag)
      throws IOException {
    // Parse the message from json string
    long chartId = JSONUtil.parseObj(message).getLong(BiMqConstant.BI_MQ_MESSAGE_CHART_ID_KEY);
    long userId = JSONUtil.parseObj(message).getLong(BiMqConstant.BI_MQ_MESSAGE_USER_ID_KEY);

    IncreaseCallQuotaReq increaseCallQuotaReq = new IncreaseCallQuotaReq();
    increaseCallQuotaReq.setUserId(userId);

    // Receive message from dead letter queue
    log.warn("BI RabbitMQ received message from dead letter queue: {}", message);

    if (StringUtils.isAnyBlank(String.valueOf(chartId), String.valueOf(userId))) {
      // Reject message when message is empty
      log.error("RabbitMQ received message from dead letter queue is empty: {}", message);
      ipFeign.increaseCallQuota(increaseCallQuotaReq);
      channel.basicNack(deliveryTag, false, false);
      throw new BusinessException(MqExceptionEnum.MQ_MESSAGE_EMPTY_ERROR.getCode(),
                                  MqExceptionEnum.MQ_MESSAGE_EMPTY_ERROR.getMessage());
    }

    // Get the chart data from database
    Chart chart = chartService.getById(chartId);
    ChatRequest chatRequest = new ChatRequest();
    ChartUpdateStatusRequest chartUpdateStatusRequest = new ChartUpdateStatusRequest();

    // If the chart data is empty, reject the message
    if (chart == null) {
      log.error("No chart data found in database, chart id: {}", chartId);
      ipFeign.increaseCallQuota(increaseCallQuotaReq);
      channel.basicNack(deliveryTag, false, false);
      throw new BusinessException(MqExceptionEnum.MQ_CHART_DATA_EMPTY_ERROR.getCode(),
                                  MqExceptionEnum.MQ_CHART_DATA_EMPTY_ERROR.getMessage());
    }

    // Update chart status to running
    Chart updateChartRunning = new Chart();
    updateChartRunning.setId(chartId);
    updateChartRunning.setStatus(ChartStatusEnum.RUNNING.getValue());
    boolean updateChartRunningResult = chartService.updateById(updateChartRunning);
    if (!updateChartRunningResult) {
      // If update failed, reject the message
      log.error("Update chart status to running failed, chart id: {}", chartId);
      ipFeign.increaseCallQuota(increaseCallQuotaReq);
      channel.basicNack(deliveryTag, false, false);
      chartUpdateStatusRequest.setId(chartId);
      chartUpdateStatusRequest.setStatus(ChartStatusEnum.FAILED);
      chartService.updateChartStatus(chartUpdateStatusRequest);
    }

    // Call AI service to generate chart
    chatRequest.setMessage(chart.getExecMessage());
    chatRequest.setKey(AiConstant.AI_API_KEY_VALUE);
    log.info("Start to call AI service to generate chart, chart id: {}", chartId);
    String chartResult = aiManager.doAiChat(chatRequest);

    // Parse the result
    String[] splitChartResult = chartResult.split(ChartConstant.CHART_CONCLUSION_DELIMITER);

    if (splitChartResult.length < ChartConstant.CHART_CONCLUSION_SPLIT_LENGTH) {
      // If the result split length is not valid, reject the message
      log.error("AI service response format error, chart id: {}", chartId);
      ipFeign.increaseCallQuota(increaseCallQuotaReq);
      channel.basicNack(deliveryTag, false, false);
      throw new BusinessException(AiExceptionEnum.AI_RESPONSE_FORMAT_ERROR.getCode(),
                                  AiExceptionEnum.AI_RESPONSE_FORMAT_ERROR.getMessage());
    }

    String chartCode = splitChartResult[1].trim();
    String chartConclusion = splitChartResult[2].trim();

    // Update success chart info into database
    Chart updateSucceedChart = new Chart();
    updateSucceedChart.setId(chart.getId());
    updateSucceedChart.setGenChartData(chartCode);
    updateSucceedChart.setGenChartConclusion(chartConclusion);
    updateSucceedChart.setStatus(ChartStatusEnum.SUCCEED.getValue());
    boolean updateSucceedChartResult = chartService.updateById(updateSucceedChart);
    if (!updateSucceedChartResult) {
      // If update failed, reject the message
      log.error("Update chart status to succeed failed, chart id: {}", chartId);
      ipFeign.increaseCallQuota(increaseCallQuotaReq);
      channel.basicNack(deliveryTag, false, false);
      chartUpdateStatusRequest.setId(chart.getId());
      chartUpdateStatusRequest.setStatus(ChartStatusEnum.FAILED);
      chartService.updateChartStatus(chartUpdateStatusRequest);
    }

    // Everything is ok, ack the message
    log.info("Chart generated successfully, chart id: {}", chartId);
    channel.basicAck(deliveryTag, false);
  }
}
