package com.glenncai.openbiplatform.common.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;

/**
 * Feign configuration for passing header
 *
 * @author Glenn Cai
 * @version 1.0 07/09/2023
 */
@Configuration
public class FeignConfig implements RequestInterceptor {

  @Override
  public void apply(RequestTemplate requestTemplate) {
    Map<String, String> headers = getHeaders(Objects.requireNonNull(getHttpServletRequest()));
    for (String headerName : headers.keySet()) {
      requestTemplate.header(headerName, getHeaders(getHttpServletRequest()).get(headerName));
    }
  }

  /**
   * Get http request
   */
  private HttpServletRequest getHttpServletRequest() {
    try {
      return ((ServletRequestAttributes) Objects.requireNonNull(
          RequestContextHolder.getRequestAttributes())).getRequest();
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Get headers from request
   *
   * @param request http request
   * @return headers
   */
  private Map<String, String> getHeaders(HttpServletRequest request) {
    Map<String, String> map = new LinkedHashMap<>();
    Enumeration<String> enumeration = request.getHeaderNames();
    while (enumeration.hasMoreElements()) {
      String key = enumeration.nextElement();
      String value = request.getHeader(key);
      map.put(key, value);
    }
    return map;
  }
}
