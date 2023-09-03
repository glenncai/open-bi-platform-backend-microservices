package com.glenncai.openbiplatform.common.common;

/**
 * Common base result
 *
 * @author Glenn Cai
 * @version 1.0 20/07/2023
 */
public class BaseResult {

  private BaseResult() {
  }

  /**
   * Success response
   *
   * @param data response data
   * @param <T>  response data type
   * @return success response
   */
  public static <T> BaseResponse<T> success(T data) {
    return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), data, ErrorCode.SUCCESS.getMessage());
  }

  /**
   * Error response
   *
   * @param errorCode custom errorCode
   * @return error response
   */
  public static BaseResponse error(ErrorCode errorCode) {
    return new BaseResponse<>(errorCode);
  }

  /**
   * Error response
   *
   * @param code    custom status code
   * @param message custom message
   * @return error response
   */
  public static BaseResponse error(int code, String message) {
    return new BaseResponse<>(code, null, message);
  }

  /**
   * Error response
   *
   * @param errorCode custom errorCode
   * @param message   custom message
   * @return error response
   */
  public static BaseResponse error(ErrorCode errorCode, String message) {
    return new BaseResponse<>(errorCode, message);
  }
}
