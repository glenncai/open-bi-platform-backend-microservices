package com.glenncai.openbiplatform.ip;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ComponentScan("com.glenncai.openbiplatform")
@Slf4j
public class IpApplication {

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(IpApplication.class);
    Environment environment = application.run(args).getEnvironment();
    String serverPort = environment.getProperty("server.port");
    String contextPath = environment.getProperty("server.servlet.context-path");
    log.info("Running successfully!");
    log.info("Access URLs: http://127.0.0.1:{}{}", serverPort, contextPath);
  }

}
