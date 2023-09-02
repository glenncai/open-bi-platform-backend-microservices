package com.glenncai.openbiplatform.configserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableConfigServer
@Slf4j
public class ConfigServerApplication {

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(ConfigServerApplication.class);
    Environment environment = application.run(args).getEnvironment();
    String serverPort = environment.getProperty("server.port");
    log.info("Running successfully!");
    log.info("Access URLs: http://127.0.0.1:{}", serverPort);
  }
}
