package com.glenncai.openbiplatform.user.feign;

import com.glenncai.openbiplatform.common.model.dto.InitIpReq;
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
}
