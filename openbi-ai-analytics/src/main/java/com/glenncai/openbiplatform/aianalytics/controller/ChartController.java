package com.glenncai.openbiplatform.aianalytics.controller;

import com.glenncai.openbiplatform.aianalytics.model.dto.ChartGenByAiRequest;
import com.glenncai.openbiplatform.aianalytics.service.ChartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Chart controller (RESTful API)
 *
 * @author Glenn Cai
 * @version 1.0 06/09/2023
 */
@RestController
@RequestMapping("/chart")
@Slf4j
public class ChartController {

  @Resource
  private ChartService chartService;

  /**
   * Chart generate by AI asynchronously (RabbitMQ) api
   *
   * @param multipartFile       file
   * @param chartGenByAiRequest chart gen by ai request body
   * @param httpServletRequest  http request
   */
  @PostMapping("/gen")
  public void genChartByAiAsyncMq(@RequestPart("file") MultipartFile multipartFile,
                                  ChartGenByAiRequest chartGenByAiRequest,
                                  HttpServletRequest httpServletRequest) {
    chartService.genChartByAiAsyncMq(multipartFile, chartGenByAiRequest, httpServletRequest);
  }
}
