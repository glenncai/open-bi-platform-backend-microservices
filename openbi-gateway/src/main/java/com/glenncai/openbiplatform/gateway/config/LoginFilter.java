package com.glenncai.openbiplatform.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Gateway login verification as the first line of defense
 *
 * @author Glenn Cai
 * @version 1.0 04/09/2023
 */
@Component
@Slf4j
public class LoginFilter implements Ordered, GlobalFilter {

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    String path = exchange.getRequest().getURI().getPath();

    // Exclude the request path that does not need to be verified, such as login, register
    if (path.contains("/api/user/login") || path.contains("/api/user/register")) {
      log.info("Path: {}, no need to verify", path);
      return chain.filter(exchange);
    } else {
      log.info("Path: {}, need to verify", path);
    }

    return chain.filter(exchange);
  }

  /**
   * The smaller the number, the higher the priority
   *
   * @return priority
   */
  @Override
  public int getOrder() {
    return 0;
  }
}
