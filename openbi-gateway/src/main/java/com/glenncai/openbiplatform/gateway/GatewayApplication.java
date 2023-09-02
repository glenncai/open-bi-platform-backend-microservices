package com.glenncai.openbiplatform.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ComponentScan("com.glenncai.openbiplatform")
@Slf4j
public class GatewayApplication {

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(GatewayApplication.class);
    Environment environment = application.run(args).getEnvironment();
    String serverPort = environment.getProperty("server.port");
    log.info("Running successfully!");
    log.info("Access URLs: http://127.0.0.1:{}", serverPort);
  }
}
