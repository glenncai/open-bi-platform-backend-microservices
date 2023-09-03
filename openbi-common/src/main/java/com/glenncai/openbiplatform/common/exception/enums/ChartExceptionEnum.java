package com.glenncai.openbiplatform.exception.enums;

import com.glenncai.openbiplatform.common.ErrorCode;

/**
 * This enum is for chart exception constant in code and message pair
 *
 * @author Glenn Cai
 * @version 1.0 07/25/2023
 */
public enum ChartExceptionEnum {

  CHART_EMPTY_ERROR(ErrorCode.PARAM_ERROR.getCode(), "Excel file cannot be empty."),
  CHART_TYPE_ERROR(ErrorCode.PARAM_ERROR.getCode(), "Excel file type is incorrect."),
  CHART_NO_QUOTA_ERROR(ErrorCode.OPERATION_ERROR.getCode(), "You have no remaining quota."),
  CHART_EMPTY_NAME_ERROR(ErrorCode.PARAM_ERROR.getCode(), "Chart name cannot be empty."),
  CHART_EMPTY_TYPE_ERROR(ErrorCode.PARAM_ERROR.getCode(), "Chart type cannot be empty."),
  CHART_EMPTY_GOAL_ERROR(ErrorCode.PARAM_ERROR.getCode(), "Chart goal cannot be empty."),
  CHART_FILE_SIZE_ERROR(ErrorCode.PARAM_ERROR.getCode(), "The maximum file size is 1MB."),
  CHART_FILE_EXTENSION_ERROR(ErrorCode.PARAM_ERROR.getCode(),
                             "Only support xls, xlsx and csv file."),
  CHART_SAVE_ERROR(ErrorCode.SERVER_ERROR.getCode(), ErrorCode.SERVER_ERROR.getMessage()),
  CHART_AI_THREAD_POOL_FULL_ERROR(ErrorCode.OPERATION_ERROR.getCode(),
                                  "System is busy, please try again later.");

  /**
   * Error code
   */
  private final int code;

  /**
   * Error message
   */
  private final String message;

  ChartExceptionEnum(int code, String message) {
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
