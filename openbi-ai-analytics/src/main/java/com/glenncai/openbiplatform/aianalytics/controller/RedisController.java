package com.glenncai.openbiplatform.aianalytics.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;

/**
 * Redis controller (RESTful API) for Redis testing
 *
 * @author Glenn Cai
 * @version 1.0 15/09/2023
 */
@RestController
@RequestMapping("/chart/redis")
@Slf4j
public class RedisController {

  @Resource
  private RedisTemplate redisTemplate;

  @RequestMapping("/set/{key}/{value}")
  public String set(@PathVariable String key, @PathVariable String value) {
    redisTemplate.opsForValue().set(key, value, 3600, TimeUnit.SECONDS);
    log.info("key: {}, value: {}", key, value);
    return "success";
  }

  @RequestMapping("/get/{key}")
  public Object get(@PathVariable String key) {
    Object object = redisTemplate.opsForValue().get(key);
    log.info("key: {}, value: {}", key, object);
    return object;
  }
}
