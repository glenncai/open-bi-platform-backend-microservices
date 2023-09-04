package com.glenncai.openbiplatform.ip.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glenncai.openbiplatform.common.constant.IpConstant;
import com.glenncai.openbiplatform.common.exception.BusinessException;
import com.glenncai.openbiplatform.common.exception.enums.IpExceptionEnum;
import com.glenncai.openbiplatform.ip.mapper.IpMapper;
import com.glenncai.openbiplatform.ip.model.dto.InitIpReq;
import com.glenncai.openbiplatform.ip.model.entity.Ip;
import com.glenncai.openbiplatform.ip.service.IpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * IP service implementation
 *
 * @author Glenn Cai
 * @version 1.0 03/09/2023
 */
@Service
@Slf4j
public class IpServiceImpl extends ServiceImpl<IpMapper, Ip>
    implements IpService {

  /**
   * Initialize client ip
   *
   * @param initIpReq init ip request body
   */
  @Override
  public void initIp(InitIpReq initIpReq) {
    // Check if the ip exists in the table
    String clientIp = initIpReq.getIp();
    Ip ip = this.lambdaQuery().eq(Ip::getIp, clientIp).one();

    if (ip == null) {
      Ip newIp = new Ip();
      newIp.setIp(clientIp);
      boolean createIpResult = this.save(newIp);
      if (!createIpResult) {
        log.error("Client ip: {}, Error message: Initialize ip failed", clientIp);
        throw new BusinessException(IpExceptionEnum.IP_INIT_ERROR.getCode(),
                                    IpExceptionEnum.IP_INIT_ERROR.getMessage());
      }
      return;
    }

    // Check if the date is the same day, if not, reset the remaining quota to max
    Date lastCallDate = ip.getLastCallDate();
    boolean isSameDay = DateUtils.isSameDay(lastCallDate, new Date());
    if (!isSameDay) {
      ip.setRemainingQuota(IpConstant.MAX_REMAINING_QUOTA);
    }
  }
}




