package com.glenncai.openbiplatform.common.aspect;

import cn.hutool.json.JSONObject;
import com.glenncai.openbiplatform.common.annotation.PreAuthorize;
import com.glenncai.openbiplatform.common.exception.BusinessException;
import com.glenncai.openbiplatform.common.exception.enums.AuthExceptionEnum;
import com.glenncai.openbiplatform.common.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * Authentication and authorization aspect
 *
 * @author Glenn Cai
 * @version 1.0 05/09/2023
 */
@Aspect
@Component
@Slf4j
public class AuthAspect {

  @Around("@annotation(preAuthorize)")
  public Object doAround(ProceedingJoinPoint joinPoint, PreAuthorize preAuthorize)
      throws Throwable {
    List<String> anyRole =
        Arrays.stream(preAuthorize.anyRole()).filter(StringUtils::isNotBlank).toList();
    String mustRole = preAuthorize.mustRole();
    RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
    HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
    String token = JwtUtils.getJwtFromAuthorizationHeader(request);

    // Authentication
    boolean authenticationResult = JwtUtils.validateToken(token);
    if (!authenticationResult) {
      throw new BusinessException(AuthExceptionEnum.AUTH_INVALID_TOKEN_ERROR.getCode(),
                                  AuthExceptionEnum.AUTH_INVALID_TOKEN_ERROR.getMessage());
    }

    // Parse token and check role
    JSONObject payload = JwtUtils.getFilteredPayloads(token);
    String role = payload.getStr("role");
    if (role == null) {
      throw new BusinessException(AuthExceptionEnum.AUTH_INVALID_TOKEN_ERROR.getCode(),
                                  AuthExceptionEnum.AUTH_INVALID_TOKEN_ERROR.getMessage());
    }

    // Authorization
    if (!CollectionUtils.isEmpty(anyRole)) {
      boolean anyRoleResult = anyRole.contains(role);
      if (!anyRoleResult) {
        throw new BusinessException(AuthExceptionEnum.AUTH_NO_PERMISSION_ERROR.getCode(),
                                    AuthExceptionEnum.AUTH_NO_PERMISSION_ERROR.getMessage());
      }
    }
    if (StringUtils.isNotBlank(mustRole)) {
      boolean mustRoleResult = mustRole.equals(role);
      if (!mustRoleResult) {
        throw new BusinessException(AuthExceptionEnum.AUTH_NO_PERMISSION_ERROR.getCode(),
                                    AuthExceptionEnum.AUTH_NO_PERMISSION_ERROR.getMessage());
      }
    }

    // Pass auth check, continue to execute
    return joinPoint.proceed();
  }
}
