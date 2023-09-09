package com.glenncai.openbiplatform.ip.controller;

import com.glenncai.openbiplatform.common.annotation.PreAuthorize;
import com.glenncai.openbiplatform.common.constant.UserConstant;
import com.glenncai.openbiplatform.ip.model.dto.CheckQuotaReq;
import com.glenncai.openbiplatform.ip.model.dto.GetIpInfoReq;
import com.glenncai.openbiplatform.ip.model.dto.IncreaseCallQuotaReq;
import com.glenncai.openbiplatform.ip.model.dto.InitIpReq;
import com.glenncai.openbiplatform.ip.model.dto.ReduceCallQuotaReq;
import com.glenncai.openbiplatform.ip.model.entity.Ip;
import com.glenncai.openbiplatform.ip.service.IpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * IP controller (RESTful API)
 *
 * @author Glenn Cai
 * @version 1.0 04/09/2023
 */
@RestController
@RequestMapping("/ip")
@Slf4j
public class IpController {

  @Resource
  private IpService ipService;

  /**
   * Initialize IP
   *
   * @param initIpReq init IP request body
   */
  @PostMapping("/init")
  public void initIp(@RequestBody InitIpReq initIpReq) {
    ipService.initIp(initIpReq);
  }

  /**
   * Check if the user has remaining AI quota
   *
   * @param checkQuotaReq check quota request body
   */
  @PostMapping("/quota/check")
//  @PreAuthorize(anyRole = {UserConstant.DEFAULT_ROLE, UserConstant.ADMIN_ROLE})
  public void checkRemainingQuota(@RequestBody CheckQuotaReq checkQuotaReq) {
    ipService.checkRemainingQuota(checkQuotaReq);
  }

  /**
   * Get IP info by user id
   *
   * @param userId user id
   * @return IP info
   */
  @GetMapping("/user/{userId}")
  @PreAuthorize(anyRole = {UserConstant.DEFAULT_ROLE, UserConstant.ADMIN_ROLE})
  public Ip getIpInfoByUserId(@PathVariable Long userId) {
    GetIpInfoReq getIpInfoReq = new GetIpInfoReq();
    getIpInfoReq.setUserId(userId);
    return ipService.getIpInfoByUserId(getIpInfoReq);
  }

  /**
   * Reduce call quota by 1 for the user
   *
   * @param reduceCallQuotaReq reduce call quota request body
   */
  @PostMapping("/quota/reduce")
  @PreAuthorize(anyRole = {UserConstant.DEFAULT_ROLE, UserConstant.ADMIN_ROLE})
  public void reduceCallQuota(@RequestBody ReduceCallQuotaReq reduceCallQuotaReq) {
    ipService.reduceCallQuota(reduceCallQuotaReq);
  }

  /**
   * Increase call quota by 1 for the user
   *
   * @param increaseCallQuotaReq increase call quota request body
   */
  @PostMapping("/quota/increase")
  @PreAuthorize(anyRole = {UserConstant.DEFAULT_ROLE, UserConstant.ADMIN_ROLE})
  public void increaseCallQuota(@RequestBody IncreaseCallQuotaReq increaseCallQuotaReq) {
    ipService.increaseCallQuota(increaseCallQuotaReq);
  }
}
