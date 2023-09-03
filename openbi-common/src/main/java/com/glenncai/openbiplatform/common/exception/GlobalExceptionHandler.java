package com.glenncai.openbiplatform.common.exception;

import com.glenncai.openbiplatform.common.common.BaseResponse;
import com.glenncai.openbiplatform.common.common.BaseResult;
import com.glenncai.openbiplatform.common.common.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler
 *
 * @author Glenn Cai
 * @version 1.0 21/07/2023
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public BaseResponse<?> businessExceptionHandler(BusinessException e) {
    log.error("Business exception: {}", e.getMessage());
    return BaseResult.error(e.getCode(), e.getMessage());
  }

  @ExceptionHandler(RuntimeException.class)
  public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
    log.error("Runtime exception: {}", e.getMessage());
    return BaseResult.error(ErrorCode.SERVER_ERROR.getCode(), ErrorCode.SERVER_ERROR.getMessage());
  }
}
