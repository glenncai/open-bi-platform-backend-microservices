package com.glenncai.openbiplatform.gateway.utils;

import cn.hutool.crypto.GlobalBouncyCastleProvider;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.RegisteredPayload;
import com.glenncai.openbiplatform.gateway.constant.JwtConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;

/**
 * JWT utils
 *
 * @author Glenn Cai
 * @version 1.0 05/09/2023
 */
@Slf4j
public class JwtUtils {

  private JwtUtils() {
  }

  /**
   * Validate JWT token
   *
   * @param token token
   * @return validate result
   */
  public static boolean validateToken(String token) {
    GlobalBouncyCastleProvider.setUseBouncyCastle(false);
    JWT jwt = JWTUtil.parseToken(token).setKey(JwtConstant.key.getBytes());
    boolean validateResult = jwt.validate(0);
    log.info("Token validate result: {}", validateResult);
    return validateResult;
  }

  /**
   * Get JWT filter JWT token payload
   *
   * @param token token
   * @return payload
   */
  public static JSONObject getFilteredPayloads(String token) {
    GlobalBouncyCastleProvider.setUseBouncyCastle(false);
    JWT jwt = JWTUtil.parseToken(token).setKey(JwtConstant.key.getBytes());
    JSONObject payloads = jwt.getPayloads();
    payloads.remove(RegisteredPayload.ISSUED_AT);
    payloads.remove(RegisteredPayload.EXPIRES_AT);
    payloads.remove(RegisteredPayload.NOT_BEFORE);
    log.info("Token payloads: {}", payloads);
    return payloads;
  }

  /**
   * Get JWT token from http request authorization header
   *
   * @param exchange http exchange
   * @return JWT token
   */
  public static String getJwtFromAuthorizationHeader(ServerWebExchange exchange) {
    String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      return authorizationHeader.substring(7);
    }
    return null;
  }
}
