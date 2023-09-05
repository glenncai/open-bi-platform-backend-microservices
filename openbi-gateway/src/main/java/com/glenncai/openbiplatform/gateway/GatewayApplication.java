package com.glenncai.openbiplatform.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Optional;

@SpringBootApplication
@EnableEurekaClient
@Slf4j
public class GatewayApplication {

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(GatewayApplication.class);
    Environment environment = application.run(args).getEnvironment();
    String serverPort = environment.getProperty("server.port");
    log.info("Running successfully!");
    log.info("Access URLs: http://127.0.0.1:{}", serverPort);
  }

  /**
   * Ip-based rate limiting
   *
   * @return KeyResolver
   */
  @Bean("ipKeyResolver")
  public KeyResolver ipKeyResolver() {
    return exchange -> Mono.just(
        String.valueOf(Optional.ofNullable(exchange.getRequest().getRemoteAddress())
                               .map(InetSocketAddress::getAddress)
                               .map(InetAddress::getHostAddress)
                               .map(Mono::just)
                               .orElse(Mono.empty())));
  }
}
