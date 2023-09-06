package com.glenncai.openbiplatform.user.feign;

import com.glenncai.openbiplatform.common.model.dto.CheckQuotaReq;
import com.glenncai.openbiplatform.common.model.dto.InitIpReq;
import com.glenncai.openbiplatform.common.model.dto.ReduceCallQuotaReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Ip service feign
 *
 * @author Glenn Cai
 * @version 1.0 04/09/2023
 */
@FeignClient(name = "OPEN-BI-IP-SERVICE/api/ip")
public interface IpFeign {

  /**
   * Initialize IP
   *
   * @param initIpReq init IP request body
   */
  @PostMapping("/init")
  void initIp(@RequestBody InitIpReq initIpReq);

  /**
   * Check if the user has remaining AI quota
   *
   * @param checkQuotaReq check quota request body
   */
  @PostMapping("/quota/check")
  void checkRemainingQuota(@RequestBody CheckQuotaReq checkQuotaReq);

  /**
   * Reduce call quota by 1 for the user
   *
   * @param reduceCallQuotaReq reduce call quota request body
   */
  @PostMapping("/quota/reduce")
  void reduceCallQuota(@RequestBody ReduceCallQuotaReq reduceCallQuotaReq);
}
