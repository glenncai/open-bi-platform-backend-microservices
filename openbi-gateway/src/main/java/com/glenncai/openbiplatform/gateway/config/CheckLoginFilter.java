package com.glenncai.openbiplatform.gateway.config;

import cn.hutool.core.text.CharSequenceUtil;
import com.glenncai.openbiplatform.gateway.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
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
public class CheckLoginFilter implements Ordered, GlobalFilter {

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    String path = exchange.getRequest().getURI().getPath();

    // Exclude the request path that does not need to be verified
    if (path.contains("/user/login") || path.contains("/user/register")) {
      log.info("Path: {}, no need to verify", path);
      return chain.filter(exchange);
    } else {
      log.info("Path: {}, need to verify", path);
    }

    String token = JwtUtils.getJwtFromAuthorizationHeader(exchange);

    // Check if token exists in Authorization header
    if (CharSequenceUtil.isBlank(token)) {
      log.info("No token found in Authorization header");
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    // Validate token
    boolean isValid = JwtUtils.validateToken(token);
    if (!isValid) {
      log.info("Token is invalid or expired. Token: {}", token);
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
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
