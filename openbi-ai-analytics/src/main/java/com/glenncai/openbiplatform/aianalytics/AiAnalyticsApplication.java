package com.glenncai.openbiplatform.aianalytics;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

@SpringBootApplication
@MapperScan("com.glenncai.openbiplatform.aianalytics.mapper")
@ComponentScan("com.glenncai.openbiplatform") // scan all packages like common module
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@Slf4j
public class AiAnalyticsApplication {

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(AiAnalyticsApplication.class);
    Environment environment = application.run(args).getEnvironment();
    String serverPort = environment.getProperty("server.port");
    String contextPath = environment.getProperty("server.servlet.context-path");
    log.info("Running successfully!");
    log.info("Access URLs: http://127.0.0.1:{}{}", serverPort, contextPath);
  }

}
