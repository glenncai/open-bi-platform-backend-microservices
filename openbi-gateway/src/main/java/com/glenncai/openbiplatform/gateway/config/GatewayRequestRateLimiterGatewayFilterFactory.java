package com.glenncai.openbiplatform.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * This class is for xxx.
 *
 * @author Glenn Cai
 * @version 1.0 5/9/2023
 */
@Component
@Slf4j
public class GatewayRequestRateLimiterGatewayFilterFactory extends
                                                           RequestRateLimiterGatewayFilterFactory {

  private final RateLimiter defaultRateLimiter;
  private final KeyResolver defaultKeyResolver;

  public GatewayRequestRateLimiterGatewayFilterFactory(RateLimiter defaultRateLimiter,
                                                       @Qualifier("ipKeyResolver")
                                                       KeyResolver defaultKeyResolver) {
    super(defaultRateLimiter, defaultKeyResolver);
    this.defaultRateLimiter = defaultRateLimiter;
    this.defaultKeyResolver = defaultKeyResolver;
  }

  @Override
  public GatewayFilter apply(Config config) {
    KeyResolver resolver = getOrDefault(config.getKeyResolver(), defaultKeyResolver);
    RateLimiter<Object> limiter =
        (RateLimiter) this.getOrDefault(config.getRateLimiter(), defaultRateLimiter);
    return (exchange, chain) -> resolver.resolve(exchange).flatMap(key -> {
      String routeId = config.getRouteId();
      if (routeId == null) {
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        if (route != null) {
          routeId = route.getId();
        }
      }
      String finalRouteId = routeId;
      return limiter.isAllowed(routeId, key).flatMap(response -> {
        for (Map.Entry<String, String> header : response.getHeaders().entrySet()) {
          exchange.getResponse().getHeaders().add(header.getKey(), header.getValue());
        }
        if (response.isAllowed()) {
          return chain.filter(exchange);
        }
        log.warn("The request has been throttled, routeId: {}, key: {}", finalRouteId, key);
        ServerHttpResponse httpResponse = exchange.getResponse();
        httpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        if (!httpResponse.getHeaders().containsKey("Content-Type")) {
          httpResponse.getHeaders().add("Content-Type", "application/json");
        }

        DataBuffer buffer = httpResponse.bufferFactory().wrap(("{\n"
            + "  \"code\": 42900,"
            + "  \"message\": \"Too many requests in 1 second. Try again later.\","
            + "  \"data\": null,"
            + "}").getBytes(StandardCharsets.UTF_8));
        return httpResponse.writeWith(Mono.just(buffer));
      });
    });
  }

  private <T> T getOrDefault(T configValue, T defaultValue) {
    return configValue != null ? configValue : defaultValue;
  }
}
