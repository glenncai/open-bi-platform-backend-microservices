package com.glenncai.openbiplatform.common.common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Common response
 *
 * @author Glenn Cai
 * @version 1.0 20/07/2023
 */
@Data
public class BaseResponse<T> implements Serializable {

  @Serial
  private static final long serialVersionUID = -4702764798156570113L;

  private int code;

  private T data;

  private String message;

  /**
   * BaseResponse with code, data, message
   *
   * @param code    status code
   * @param data    response data
   * @param message response message
   */
  public BaseResponse(int code, T data, String message) {
    this.code = code;
    this.data = data;
    this.message = message;
  }

  /**
   * BaseResponse with code, data, but no message
   *
   * @param code status code
   * @param data response data
   */
  public BaseResponse(int code, T data) {
    this.code = code;
    this.data = data;
    this.message = "";
  }

  /**
   * BaseResponse with custom errorCode, including code and message but no data
   *
   * @param errorCode custom errorCode
   */
  public BaseResponse(ErrorCode errorCode) {
    this.code = errorCode.getCode();
    this.data = null;
    this.message = errorCode.getMessage();
  }

  /**
   * BaseResponse with custom errorCode, including code and message but no data
   *
   * @param errorCode custom errorCode
   * @param message   custom message
   */
  public BaseResponse(ErrorCode errorCode, String message) {
    this.code = errorCode.getCode();
    this.data = null;
    this.message = message;
  }
}
