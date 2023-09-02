package com.glenncai.openbiplatform.common.aspect;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Log aspect to log request info
 *
 * @author Glenn Cai
 * @version 1.0 1/9/2023
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
  public LogAspect() {
    log.info("Starting log aspect \uD83D\uDE80\uD83D\uDE80\uD83D\uDE80\uD83D\uDE80\uD83D\uDE80");
  }

  @Pointcut("execution(* com.glenncai.openbiplatform.*.controller.*.*(..))")
  public void controllerPointcut() {
  }

  @Before("controllerPointcut()")
  public void doBefore(JoinPoint joinPoint) {

    // Generate unique request id
    MDC.put("LOG_ID", System.currentTimeMillis() + RandomUtil.randomString(3));

    // Get request info
    RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
    HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
    Signature signature = joinPoint.getSignature();
    String name = signature.getName();

    // Log request info
    log.info("------------- Start -------------");
    log.info("Request URL: {} {}", request.getRequestURL().toString(), request.getMethod());
    log.info("Class Method: {}.{}", signature.getDeclaringTypeName(), name);
    log.info("Remote Address: {}", request.getRemoteAddr());

    // Log request parameters
    Object[] args = joinPoint.getArgs();

    // Exclude special parameters, such as file
    Object[] arguments = new Object[args.length];
    for (int i = 0; i < args.length; i++) {
      if (args[i] instanceof ServletRequest
          || args[i] instanceof ServletResponse
          || args[i] instanceof MultipartFile) {
        continue;
      }
      arguments[i] = args[i];
    }

    // Exclude sensitive parameters, such as password
    String[] excludeProperties = {"password", "confirmPassword"};
    PropertyPreFilters filters = new PropertyPreFilters();
    PropertyPreFilters.MySimplePropertyPreFilter excludeFilter = filters.addFilter();
    excludeFilter.addExcludes(excludeProperties);
    log.info("Request Parameters: {}", JSON.toJSONString(arguments, excludeFilter));
  }

  @Around("controllerPointcut()")
  public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    long startTime = System.currentTimeMillis();
    Object result = proceedingJoinPoint.proceed();

    // Exclude sensitive parameters, such as password
    String[] excludeProperties = {"password", "confirmPassword"};
    PropertyPreFilters filters = new PropertyPreFilters();
    PropertyPreFilters.MySimplePropertyPreFilter excludeFilter = filters.addFilter();
    excludeFilter.addExcludes(excludeProperties);

    log.info("Result: {}", JSON.toJSONString(result, excludeFilter));
    log.info("------------- End  Time consuming \uD83D\uDD51：{} ms -------------",
             System.currentTimeMillis() - startTime);
    return result;
  }
}
