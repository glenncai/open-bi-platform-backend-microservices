package com.glenncai.openbiplatform.gateway.controller;

import com.glenncai.openbiplatform.gateway.common.BaseResponse;
import com.glenncai.openbiplatform.gateway.common.BaseResult;
import com.glenncai.openbiplatform.gateway.common.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Circuit breaker fallback controller
 *
 * @author Glenn Cai
 * @version 1.0 07/09/2023
 */
@RestController
@Slf4j
public class FallbackController {

  @GetMapping("/fallback/user")
  public BaseResponse userServiceFallback() {
    log.error("User service is not available.");
    String message = "Opps... User service is not available. Please try again later.";
    return BaseResult.error(ErrorCode.SERVICE_UNAVAILABLE_ERROR, message);
  }

  @GetMapping("/fallback/ip")
  public BaseResponse ipServiceFallback() {
    log.error("IP service is not available.");
    String message = "Opps... IP service is not available. Please try again later.";
    return BaseResult.error(ErrorCode.SERVICE_UNAVAILABLE_ERROR, message);
  }

  @GetMapping("/fallback/aikey")
  public BaseResponse aikeyServiceFallback() {
    log.error("AIKey service is not available.");
    String message = "Opps... AIKey service is not available. Please try again later.";
    return BaseResult.error(ErrorCode.SERVICE_UNAVAILABLE_ERROR, message);
  }

  @GetMapping("/fallback/chart")
  public BaseResponse aianalyticsServiceFallback() {
    log.error("AI Analytics service is not available.");
    String message = "Opps... AI Analytics service is not available. Please try again later.";
    return BaseResult.error(ErrorCode.SERVICE_UNAVAILABLE_ERROR, message);
  }
}
