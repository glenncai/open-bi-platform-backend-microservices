package com.glenncai.openbiplatform.ip.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glenncai.openbiplatform.ip.model.dto.InitIpReq;
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
}
