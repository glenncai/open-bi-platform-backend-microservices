package com.glenncai.openbiplatform.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Global CORS configuration
 *
 * @author Glenn Cai
 * @version 1.0 03/09/2023
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

  /**
   * @param registry cors registry
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowCredentials(true) // Allow cookie
            .allowedOriginPatterns("*")
            .allowedMethods("*")
            .allowedHeaders("*")
            .exposedHeaders("*")
            .maxAge(3600);
  }
}
