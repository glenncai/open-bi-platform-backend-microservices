package com.glenncai.openbiplatform.aianalytics.mq;

import com.glenncai.openbiplatform.aianalytics.constant.BiMqConstant;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;

/**
 * BI RabbitMQ message queue and exchange initialization (Automatically)
 *
 * @author Glenn Cai
 * @version 1.0 08/10/2023
 */
@Slf4j
@Component
public class BiMqBeanInit {

  @Value("${spring.rabbitmq.host}")
  private String host;

  @Value("${spring.rabbitmq.username}")
  private String username;

  @Value("${spring.rabbitmq.password}")
  private String password;

  @PostConstruct
  public void init() {
    try {
      ConnectionFactory factory = new ConnectionFactory();

      // RabbitMQ configuration
      factory.setHost(host);
      factory.setUsername(username);
      factory.setPassword(password);

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
      log.info("BI RabbitMQ message queue and exchange initialization completed");
    } catch (Exception e) {
      log.error("BI RabbitMQ message queue and exchange initialization failed");
      e.printStackTrace();
    }
  }
}
