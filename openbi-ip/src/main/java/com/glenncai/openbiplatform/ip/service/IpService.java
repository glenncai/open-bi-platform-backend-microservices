package com.glenncai.openbiplatform.ip.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glenncai.openbiplatform.ip.model.dto.CheckQuotaReq;
import com.glenncai.openbiplatform.ip.model.dto.GetIpInfoReq;
import com.glenncai.openbiplatform.ip.model.dto.InitIpReq;
import com.glenncai.openbiplatform.ip.model.dto.ReduceCallQuotaReq;
import com.glenncai.openbiplatform.ip.model.entity.Ip;

/**
 * IP service
 *
 * @author Glenn Cai
 * @version 1.0 03/09/2023
 */
public interface IpService extends IService<Ip> {

  /**
   * Initialize client ip
   *
   * @param initIpReq init ip request body
   */
  void initIp(InitIpReq initIpReq);

  /**
   * Check if the user has remaining AI quota
   *
   * @param checkQuotaReq check quota request body
   */
  void checkRemainingQuota(CheckQuotaReq checkQuotaReq);

  /**
   * Get ip info by user id
   *
   * @param getIpInfoReq get ip info request body
   * @return ip info
   */
  Ip getIpInfoByUserId(GetIpInfoReq getIpInfoReq);

  /**
   * Reduce call quota by 1 for the user
   *
   * @param reduceCallQuotaReq reduce call quota request body
   */
  void reduceCallQuota(ReduceCallQuotaReq reduceCallQuotaReq);
}
