package com.glenncai.openbiplatform.common.exception.enums;

import com.glenncai.openbiplatform.common.common.ErrorCode;

/**
 * Enum for IP exception constant in code and message pair
 *
 * @author Glenn Cai
 * @version 1.0 26/07/2023
 */
public enum IpExceptionEnum {

  IP_INIT_ERROR(ErrorCode.OPERATION_ERROR.getCode(), ErrorCode.OPERATION_ERROR.getMessage()),
  IP_NOT_EXIST_ERROR(ErrorCode.OPERATION_ERROR.getCode(),
                     ErrorCode.OPERATION_ERROR.getMessage()),
  IP_CALL_COUNT_ERROR(ErrorCode.OPERATION_ERROR.getCode(), "No remaining quota."),
  IP_UPDATE_ERROR(ErrorCode.OPERATION_ERROR.getCode(),
                  ErrorCode.OPERATION_ERROR.getMessage());

  /**
   * Error code
   */
  private final int code;

  /**
   * Error message
   */
  private final String message;

  IpExceptionEnum(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
