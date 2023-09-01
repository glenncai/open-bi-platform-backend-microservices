package com.glenncai.openbiplatform.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ComponentScan("com.glenncai.openbiplatform")
public class UserApplication {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserApplication.class);

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(UserApplication.class);
    Environment environment = application.run(args).getEnvironment();
    String serverPort = environment.getProperty("server.port");
    String contextPath = environment.getProperty("server.servlet.context-path");
    LOGGER.info("Running successfully!");
    LOGGER.info("Access URLs: http://127.0.0.1:{}{}", serverPort, contextPath);
  }

}
