package com.glenncai.openbiplatform.serviceregistry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

@SpringBootApplication
@EnableEurekaServer
@Slf4j
public class ServiceRegistryApplication {

  public static void main(String[] args) throws UnknownHostException {
    SpringApplication application = new SpringApplication(ServiceRegistryApplication.class);
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
}
