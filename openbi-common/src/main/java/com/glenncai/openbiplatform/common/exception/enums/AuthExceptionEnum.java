package com.glenncai.openbiplatform.common.exception.enums;

import com.glenncai.openbiplatform.common.common.ErrorCode;

/**
 * Enum for authentication exception constant in code and message pair
 *
 * @author Glenn Cai
 * @version 1.0 22/07/2023
 */
public enum AuthExceptionEnum {

  AUTH_EMPTY_HTTP_REQUEST_ERROR(ErrorCode.NOT_FOUND_PARAM_ERROR.getCode(),
                                ErrorCode.NOT_FOUND_PARAM_ERROR.getMessage()),
  AUTH_EMPTY_ERROR(ErrorCode.NOT_FOUND_PARAM_ERROR.getCode(),
                   "Username or password cannot be empty."),
  AUTH_USERNAME_LENGTH_ERROR(ErrorCode.PARAM_ERROR.getCode(),
                             "Username length must be between 4 and 16."),
  AUTH_USERNAME_FORMAT_ERROR(ErrorCode.PARAM_ERROR.getCode(),
                             "Username can only contain letters and numbers."),
  AUTH_PASSWORD_SPACE_ERROR(ErrorCode.PARAM_ERROR.getCode(), "Password cannot contain spaces."),
  AUTH_PASSWORD_LENGTH_ERROR(ErrorCode.PARAM_ERROR.getCode(),
                             "Password length must be greater than 8."),
  AUTH_PASSWORD_NOT_MATCH_ERROR(ErrorCode.PARAM_ERROR.getCode(),
                                "Password and confirm password do not match."),
  AUTH_USERNAME_EXIST_ERROR(ErrorCode.PARAM_ERROR.getCode(), "Username already exists."),
  AUTH_USER_NOT_EXIST_ERROR(ErrorCode.PARAM_ERROR.getCode(), "User does not exist."),
  AUTH_CREATE_USER_ERROR(ErrorCode.OPERATION_ERROR.getCode(), "Failed to create user."),
  AUTH_LOGIN_ERROR(ErrorCode.PARAM_ERROR.getCode(), "Username or password is incorrect."),
  AUTH_UPDATE_LOGIN_IP_ERROR(ErrorCode.SERVER_ERROR.getCode(),
                             "Please make sure your network is stable."),
  AUTH_NOT_LOGIN_ERROR(ErrorCode.NOT_LOGIN_ERROR.getCode(), ErrorCode.NOT_LOGIN_ERROR.getMessage()),
  AUTH_OPERATION_ERROR(ErrorCode.OPERATION_ERROR.getCode(), ErrorCode.OPERATION_ERROR.getMessage()),
  AUTH_DISABLE_USER_ERROR(ErrorCode.OPERATION_ERROR.getCode(), "Failed to disable user."),
  AUTH_INVALID_TOKEN_ERROR(ErrorCode.UNAUTHORIZED_ERROR.getCode(),
                           "Your authentication token is invalid. Please login again."),
  AUTH_NO_PERMISSION_ERROR(ErrorCode.FORBIDDEN_ERROR.getCode(),
                           "You do not have permission to access this resource.");

  /**
   * Error code
   */
  private final int code;

  /**
   * Error message
   */
  private final String message;

  AuthExceptionEnum(int code, String message) {
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
