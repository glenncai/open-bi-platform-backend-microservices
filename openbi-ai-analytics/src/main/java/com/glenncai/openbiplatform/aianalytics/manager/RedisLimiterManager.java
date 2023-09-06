package com.glenncai.openbiplatform.aianalytics.manager;

import com.glenncai.openbiplatform.common.common.ErrorCode;
import com.glenncai.openbiplatform.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Provides Redis rate limiter service
 * <p>
 * Redis rate limiter is used to limit the number of requests per unit time for a certain user /
 * method
 *
 * @author Glenn Cai
 * @version 1.0 30/07/2023
 */
@Service
@Slf4j
public class RedisLimiterManager {

  @Resource
  private RedissonClient redissonClient;

  /**
   * Rate limiter operation
   *
   * @param key of the rate limiter, like user id to distinguish different users
   */
  public void doRateLimit(String key) {
    RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
    // Each user in all servers (distributed system) can only send one request every five minutes
    rateLimiter.trySetRate(RateType.OVERALL, 30, 1, RateIntervalUnit.SECONDS);

    // Acquire a permit, if it is not available, then wait for the specified time
    boolean canDo = rateLimiter.tryAcquire(1);
    if (!canDo) {
      throw new BusinessException(ErrorCode.TOO_MANY_REQUESTS_ERROR);
    }
  }
}
