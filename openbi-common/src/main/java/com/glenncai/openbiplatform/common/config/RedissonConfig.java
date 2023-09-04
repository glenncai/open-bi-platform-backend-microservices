package com.glenncai.openbiplatform.common.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson configuration
 *
 * @author Glenn Cai
 * @version 1.0 29/07/2023
 */
@Data
@Configuration
public class RedissonConfig {

  @Value("${spring.redis.database}")
  private Integer database;
  @Value("${spring.redis.host}")
  private String host;
  @Value("${spring.redis.port}")
  private Integer port;
  @Value("${spring.redis.password}")
  private String password;

  @Bean
  public RedissonClient redissonClient() {
    Config config = new Config();
    config.useSingleServer()
          .setAddress("redis://" + host + ":" + port)
          .setDatabase(database)
          .setPassword(password);
    return Redisson.create(config);
  }
}
