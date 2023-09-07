package com.glenncai.openbiplatform.aianalytics.exception.enums;

import com.glenncai.openbiplatform.common.common.ErrorCode;

/**
 * Enum for AI exception constant in code and message pair
 *
 * @author Glenn Cai
 * @version 1.0 28/07/2023
 */
public enum AiExceptionEnum {

  AI_RESPONSE_ERROR(ErrorCode.SERVER_ERROR.getCode(), "AI response failed."),
  AI_RESPONSE_FORMAT_ERROR(ErrorCode.SERVER_ERROR.getCode(), "AI response format error.");

  /**
   * Error code
   */
  private final int code;

  /**
   * Error message
   */
  private final String message;

  AiExceptionEnum(int code, String message) {
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
