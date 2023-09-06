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
import java.net.UnknownHostException;
import java.util.Optional;

@SpringBootApplication
@EnableEurekaClient
@Slf4j
public class GatewayApplication {

  public static void main(String[] args) throws UnknownHostException {
    SpringApplication application = new SpringApplication(GatewayApplication.class);
    Environment environment = application.run(args).getEnvironment();
    String ip = InetAddress.getLocalHost().getHostAddress();
    String serverPort = environment.getProperty("server.port");
    String contextPath =
        Optional.ofNullable(environment.getProperty("server.servlet.context-path")).orElse("");
    String applicationName = environment.getProperty("spring.application.name");
    log.info("\n----------------------------------------------------------\n\t" +
                 "Application " + applicationName + " is running! Access URLs:\n\t" +
                 "Local Access URL: \t\thttp://localhost:" + serverPort + contextPath + "\n\t" +
                 "External Access URL: \thttps://" + ip + ":" + serverPort + contextPath + "\n\t" +
                 "----------------------------------------------------------");
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
