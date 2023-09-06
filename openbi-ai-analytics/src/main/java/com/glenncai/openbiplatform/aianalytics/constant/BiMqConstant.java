package com.glenncai.openbiplatform.aianalytics.constant;

/**
 * BI RabbitMQ constant
 *
 * @author Glenn Cai
 * @version 1.0 29/08/2023
 */
public final class BiMqConstant {

  // Global
  public static final String BI_MQ_HOST = "localhost";
  public static final String BI_MQ_LOGIN_USERNAME = "guest";
  public static final String BI_MQ_LOGIN_PASSWORD = "guest";
  public static final String BI_MQ_DIRECT_TYPE = "direct";
  public static final String BI_MQ_EXCHANGE_NAME = "bi_exchange";
  public static final String BI_MQ_DLX_EXCHANGE_NAME = "bi_dlx_exchange";

  // For user
  public static final String BI_MQ_USER_QUEUE_NAME = "bi_user_queue";
  public static final String BI_MQ_USER_ROUTING_KEY = "bi_user_routing_key";

  // For admin
  public static final String BI_MQ_ADMIN_QUEUE_NAME = "bi_admin_queue";
  public static final String BI_MQ_ADMIN_ROUTING_KEY = "bi_admin_routing_key";

  // For user dead letter queue
  public static final String BI_DLX_USER_QUEUE_NAME = "bi_dlx_user_queue";
  public static final String BI_DLX_USER_ROUTING_KEY = "bi_dlx_user_routing_key";

  // For admin dead letter queue
  public static final String BI_DLX_ADMIN_QUEUE_NAME = "bi_dlx_admin_queue";
  public static final String BI_DLX_ADMIN_ROUTING_KEY = "bi_dlx_admin_routing_key";

  // RabbitMQ message JSON key
  public static final String BI_MQ_MESSAGE_CHART_ID_KEY = "chartId";
  public static final String BI_MQ_MESSAGE_USER_ID_KEY = "userId";

  private BiMqConstant() {
  }
}
