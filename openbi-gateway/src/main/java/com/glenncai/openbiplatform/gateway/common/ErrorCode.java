package com.glenncai.openbiplatform.gateway.common;

/**
 * Enum for custom error code
 *
 * @author Glenn Cai
 * @version 1.0 07/20/2023
 */
public enum ErrorCode {

  SUCCESS(0, "ok."),
  PARAM_ERROR(40000, "Parameter error."),
  NOT_FOUND_PARAM_ERROR(40001, "Not found parameter."),
  NOT_LOGIN_ERROR(40100, "User not login."),
  UNAUTHORIZED_ERROR(40101, "Unauthorized."),
  FORBIDDEN_ERROR(40300, "Forbidden."),
  NOT_FOUND_ERROR(40400, "Not found."),
  TOO_MANY_REQUESTS_ERROR(42900, "Too many requests."),
  SERVER_ERROR(50000, "Internal server error."),
  OPERATION_ERROR(50001, "Operation error."),
  SERVICE_UNAVAILABLE_ERROR(50300, "Service unavailable.");

  /**
   * Error code
   */
  private final int code;

  /**
   * Error message
   */
  private final String message;

  ErrorCode(int code, String message) {
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
