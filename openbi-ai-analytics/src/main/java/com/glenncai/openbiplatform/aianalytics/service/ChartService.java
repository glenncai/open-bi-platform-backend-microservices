package com.glenncai.openbiplatform.aianalytics.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.glenncai.openbiplatform.aianalytics.model.dto.ChartGenByAiRequest;
import com.glenncai.openbiplatform.aianalytics.model.dto.ChartUpdateStatusRequest;
import com.glenncai.openbiplatform.aianalytics.model.entity.Chart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Chart service
 *
 * @author Glenn Cai
 * @version 1.0 03/09/2023
 */
public interface ChartService extends IService<Chart> {

  /**
   * Generate chart by AI asynchronously (RabbitMQ)
   *
   * @param multipartFile       multipart file
   * @param chartGenByAiRequest chart gen by ai request body
   * @param request             http request
   */
  void genChartByAiAsyncMq(MultipartFile multipartFile, ChartGenByAiRequest chartGenByAiRequest,
                           HttpServletRequest request);

  /**
   * Get chart list VO api
   *
   * @param pageNum page number
   * @param request http request
   * @return chart list VO
   */
  Page<Chart> getChartList(int pageNum, HttpServletRequest request);

  /**
   * Updates chart status
   *
   * @param chartUpdateStatusRequest chart update status request body
   */
  void updateChartStatus(ChartUpdateStatusRequest chartUpdateStatusRequest);
}
