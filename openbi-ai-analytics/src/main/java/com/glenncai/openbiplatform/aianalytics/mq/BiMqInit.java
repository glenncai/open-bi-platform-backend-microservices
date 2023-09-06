package com.glenncai.openbiplatform.aianalytics.mq;

import com.glenncai.openbiplatform.aianalytics.constant.BiMqConstant;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * BI RabbitMQ message queue and exchange initialization (Only run once)
 *
 * @author Glenn Cai
 * @version 1.0 29/08/2023
 */
public class BiMqInit {

  public static void main(String[] args) {
    try {
      ConnectionFactory factory = new ConnectionFactory();

      // RabbitMQ configuration
      factory.setHost(BiMqConstant.BI_MQ_HOST);
      factory.setUsername(BiMqConstant.BI_MQ_LOGIN_USERNAME);
      factory.setPassword(BiMqConstant.BI_MQ_LOGIN_PASSWORD);

      Connection connection = factory.newConnection();
      Channel channel = connection.createChannel();
      String exchangeName = BiMqConstant.BI_MQ_EXCHANGE_NAME;
      channel.exchangeDeclare(exchangeName, BiMqConstant.BI_MQ_DIRECT_TYPE, true);

      // Create the user queue
      String userQueueName = BiMqConstant.BI_MQ_USER_QUEUE_NAME;
      Map<String, Object> userArgs = new HashMap<>();
      // Link the user queue to the dead letter exchange
      userArgs.put("x-dead-letter-exchange", BiMqConstant.BI_MQ_DLX_EXCHANGE_NAME);
      // Link the user queue to the dead letter routing key
      userArgs.put("x-dead-letter-routing-key", BiMqConstant.BI_DLX_USER_ROUTING_KEY);
      // Declare the user channel
      channel.queueDeclare(userQueueName, true, false, false, userArgs);
      channel.queueBind(userQueueName, exchangeName, BiMqConstant.BI_MQ_USER_ROUTING_KEY);

      // Create the admin queue
      String adminQueueName = BiMqConstant.BI_MQ_ADMIN_QUEUE_NAME;
      Map<String, Object> adminArgs = new HashMap<>();
      // Link the admin queue to the dead letter exchange
      adminArgs.put("x-dead-letter-exchange", BiMqConstant.BI_MQ_DLX_EXCHANGE_NAME);
      // Link the admin queue to the dead letter routing key
      adminArgs.put("x-dead-letter-routing-key", BiMqConstant.BI_DLX_ADMIN_ROUTING_KEY);
      // Declare the admin channel
      channel.queueDeclare(adminQueueName, true, false, false, adminArgs);
      channel.queueBind(adminQueueName, exchangeName, BiMqConstant.BI_MQ_ADMIN_ROUTING_KEY);

      // Link the dead letter exchange
      String dlxExchangeName = BiMqConstant.BI_MQ_DLX_EXCHANGE_NAME;
      channel.queueDeclare(BiMqConstant.BI_DLX_USER_QUEUE_NAME, true, false, false, null);
      channel.exchangeDeclare(dlxExchangeName, BiMqConstant.BI_MQ_DIRECT_TYPE, true);
      channel.queueBind(BiMqConstant.BI_DLX_USER_QUEUE_NAME, dlxExchangeName,
                        BiMqConstant.BI_DLX_USER_ROUTING_KEY);

      channel.queueDeclare(BiMqConstant.BI_DLX_ADMIN_QUEUE_NAME, true, false, false, null);
      channel.exchangeDeclare(dlxExchangeName, BiMqConstant.BI_MQ_DIRECT_TYPE, true);
      channel.queueBind(BiMqConstant.BI_DLX_ADMIN_QUEUE_NAME, dlxExchangeName,
                        BiMqConstant.BI_DLX_ADMIN_ROUTING_KEY);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
