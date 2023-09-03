package com.glenncai.openbiplatform.common.exception;

import com.glenncai.openbiplatform.common.common.ErrorCode;

/**
 * Custom business exception
 *
 * @author Glenn Cai
 * @version 1.0 20/07/2023
 */
public class BusinessException extends RuntimeException {

  /**
   * Error code
   */
  private final int code;

  public BusinessException(int code) {
    this.code = code;
  }

  public BusinessException(int code, String message) {
    super(message);
    this.code = code;
  }

  public BusinessException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.code = errorCode.getCode();
  }

  public BusinessException(ErrorCode errorCode, String message) {
    super(message);
    this.code = errorCode.getCode();
  }

  public int getCode() {
    return code;
  }
}
