package com.glenncai.openbiplatform.common.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.crypto.GlobalBouncyCastleProvider;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.RegisteredPayload;
import com.glenncai.openbiplatform.common.constant.JwtConstant;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

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
   * Generate JWT token
   *
   * @param username username
   * @param role     role
   * @return JWT token
   */
  public static String generateToken(String username, String role) {
    GlobalBouncyCastleProvider.setUseBouncyCastle(false);
    DateTime nowTime = DateTime.now();
    DateTime expireTime = nowTime.offsetNew(DateField.HOUR, 72); // 3 days
    Map<String, Object> payload = new HashMap<>();

    // Issue time
    payload.put(RegisteredPayload.ISSUED_AT, nowTime);
    // Expire time
    payload.put(RegisteredPayload.EXPIRES_AT, expireTime);
    // Effective time
    payload.put(RegisteredPayload.NOT_BEFORE, nowTime);
    // Custom payload
    payload.put("username", username);
    payload.put("role", role);

    String token = JWTUtil.createToken(payload, JwtConstant.key.getBytes());
    log.info("Generate token: {}", token);
    return token;
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
}
