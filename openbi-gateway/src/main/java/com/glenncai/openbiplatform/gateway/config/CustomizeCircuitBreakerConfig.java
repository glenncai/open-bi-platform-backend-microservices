package com.glenncai.openbiplatform.gateway.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Circuit breaker configuration
 *
 * @author Glenn Cai
 * @version 1.0 07/09/2023
 */
@Configuration
public class CustomizeCircuitBreakerConfig {

  @Bean
  public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
    return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
        .timeLimiterConfig(
            TimeLimiterConfig.custom().timeoutDuration(Duration.ofMillis(200)).build())
        .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults()).build());
  }
}
