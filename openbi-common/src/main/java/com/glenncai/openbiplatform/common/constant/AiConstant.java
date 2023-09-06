package com.glenncai.openbiplatform.common.constant;

/**
 * AI service constant
 *
 * @author Glenn Cai
 * @version 1.0 27/07/2023
 */
public final class AiConstant {

  /**
   * AI API URL
   */
  public static final String AI_API_URL = "http://127.0.0.1:5000/api/exec";

  /**
   * AI API key value
   */
  public static final String AI_API_KEY_VALUE = "4t1ZKUFWEdezSdmAr0A_hg%3D%3D";

  public static final String AI_REDIS_RATE_LIMIT_PREFIX = "openbi:ai:";

  /**
   * Key property name
   */
  public static final String AI_API_KEY_NAME = "key";

  /**
   * Exec message property name
   */
  public static final String AI_API_MESSAGE_NAME = "message";

  private AiConstant() {
  }
}
