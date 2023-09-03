package com.glenncai.openbiplatform.common.exception.enums;

import com.glenncai.openbiplatform.common.common.ErrorCode;

/**
 * Enum for MQ exception constant in code and message pair
 *
 * @author Glenn Cai
 * @version 1.0 29/08/2023
 */
public enum MqExceptionEnum {

  MQ_MESSAGE_EMPTY_ERROR(ErrorCode.PARAM_ERROR.getCode(), "No messages are queued."),
  MQ_CHART_DATA_EMPTY_ERROR(ErrorCode.PARAM_ERROR.getCode(), "Chart data is empty.");

  /**
   * Error code
   */
  private final int code;

  /**
   * Error message
   */
  private final String message;

  MqExceptionEnum(int code, String message) {
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
