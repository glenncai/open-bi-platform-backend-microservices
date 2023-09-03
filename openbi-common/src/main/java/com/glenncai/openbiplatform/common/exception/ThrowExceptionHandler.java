package com.glenncai.openbiplatform.common.exception;

import com.glenncai.openbiplatform.common.common.ErrorCode;

/**
 * This class is for handling throw business exception. You can use BusinessException directly
 * instead of this class. But this class is more clean and reduce usage of the if statement.
 *
 * @author Glenn Cai
 * @version 1.0 23/07/2023
 */
public class ThrowExceptionHandler {

  /**
   * Throw runtime exception if condition is true
   *
   * @param condition        condition
   * @param runtimeException runtime exception
   */
  public static void throwExceptionIf(boolean condition, RuntimeException runtimeException) {
    if (condition) {
      throw runtimeException;
    }
  }

  /**
   * Throw custom business exception if condition is true
   *
   * @param condition condition
   * @param errorCode custom error code
   */
  public static void throwExceptionIf(boolean condition, ErrorCode errorCode) {
    throwExceptionIf(condition, new BusinessException(errorCode));
  }

  /**
   * Throw custom business exception if condition is true
   *
   * @param condition condition
   * @param errorCode custom error code
   * @param message   error message
   */
  public static void throwExceptionIf(boolean condition, ErrorCode errorCode, String message) {
    throwExceptionIf(condition, new BusinessException(errorCode, message));
  }

  /**
   * Throw custom business exception if condition is true
   *
   * @param condition condition
   * @param code      error code
   * @param message   error message
   */
  public static void throwExceptionIf(boolean condition, int code, String message) {
    throwExceptionIf(condition, new BusinessException(code, message));
  }
}
