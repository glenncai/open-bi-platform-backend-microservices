package com.glenncai.openbiplatform.ip.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glenncai.openbiplatform.common.common.ErrorCode;
import com.glenncai.openbiplatform.common.constant.IpConstant;
import com.glenncai.openbiplatform.common.exception.BusinessException;
import com.glenncai.openbiplatform.common.model.vo.UserVO;
import com.glenncai.openbiplatform.ip.exception.enums.IpExceptionEnum;
import com.glenncai.openbiplatform.ip.feign.UserFeign;
import com.glenncai.openbiplatform.ip.mapper.IpMapper;
import com.glenncai.openbiplatform.ip.model.dto.CheckQuotaReq;
import com.glenncai.openbiplatform.ip.model.dto.GetIpInfoReq;
import com.glenncai.openbiplatform.ip.model.dto.IncreaseCallQuotaReq;
import com.glenncai.openbiplatform.ip.model.dto.InitIpReq;
import com.glenncai.openbiplatform.ip.model.dto.ReduceCallQuotaReq;
import com.glenncai.openbiplatform.ip.model.entity.Ip;
import com.glenncai.openbiplatform.ip.service.IpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import javax.annotation.Resource;

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

  @Resource
  private UserFeign userFeign;

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

  /**
   * Check if the user has remaining AI quota
   *
   * @param checkQuotaReq check quota request body
   */
  @Override
  public void checkRemainingQuota(CheckQuotaReq checkQuotaReq) {
    long userId = checkQuotaReq.getUserId();
    if (userId < 0) {
      throw new BusinessException(ErrorCode.PARAM_ERROR);
    }

    UserVO user = userFeign.getUserInfoByUserId(userId).getData();
    Date currentDate = new Date();
    String username = user.getUsername();

    GetIpInfoReq getIpInfoReq = new GetIpInfoReq();
    getIpInfoReq.setUserId(userId);
    Ip ip = getIpInfoByUserId(getIpInfoReq);

    // Check remaining quota
    int remainingQuota = ip.getRemainingQuota();
    if (remainingQuota <= IpConstant.MIN_REMAINING_QUOTA) {
      log.error("The user '{}' has no remaining call count quota on {}", username, currentDate);
      throw new BusinessException(IpExceptionEnum.IP_REMAINING_QUOTA_ERROR.getCode(),
                                  IpExceptionEnum.IP_REMAINING_QUOTA_ERROR.getMessage());
    }
  }

  /**
   * Get ip info by user id
   *
   * @param getIpInfoReq get ip info request body
   * @return ip info
   */
  @Override
  public Ip getIpInfoByUserId(GetIpInfoReq getIpInfoReq) {
    long userId = getIpInfoReq.getUserId();
    UserVO user = userFeign.getUserInfoByUserId(userId).getData();
    String loginIp = user.getLoginIp();
    Ip ip = this.lambdaQuery().eq(Ip::getIp, loginIp).one();
    if (ip == null) {
      throw new BusinessException(IpExceptionEnum.IP_NOT_EXIST_ERROR.getCode(),
                                  IpExceptionEnum.IP_NOT_EXIST_ERROR.getMessage());
    }
    return ip;
  }

  /**
   * Reduce AI quota by 1 for the user
   *
   * @param reduceCallQuotaReq reduce call quota request body
   */
  @Override
  public void reduceCallQuota(ReduceCallQuotaReq reduceCallQuotaReq) {
    long userId = reduceCallQuotaReq.getUserId();

    GetIpInfoReq getIpInfoReq = new GetIpInfoReq();
    getIpInfoReq.setUserId(userId);
    Ip ip = getIpInfoByUserId(getIpInfoReq);

    // Reduce remaining quota by 1
    int remainingQuota = ip.getRemainingQuota();
    ip.setRemainingQuota(remainingQuota - 1);

    // Update last call date
    ip.setLastCallDate(new Date());

    boolean updateIpResult = this.updateById(ip);
    if (!updateIpResult) {
      log.error("Update t_ip table failed, try to update the data: {}", ip);
      throw new BusinessException(IpExceptionEnum.IP_UPDATE_ERROR.getCode(),
                                  IpExceptionEnum.IP_UPDATE_ERROR.getMessage());
    }
  }

  /**
   * Increase call quota by 1 for the user
   *
   * @param increaseCallQuotaReq increase call quota request body
   */
  @Override
  public void increaseCallQuota(IncreaseCallQuotaReq increaseCallQuotaReq) {
    long userId = increaseCallQuotaReq.getUserId();

    GetIpInfoReq getIpInfoReq = new GetIpInfoReq();
    getIpInfoReq.setUserId(userId);
    Ip ip = getIpInfoByUserId(getIpInfoReq);

    // Increase remaining quota by 1
    int remainingQuota = ip.getRemainingQuota();
    ip.setRemainingQuota(remainingQuota + 1);

    // Update last call date
    ip.setLastCallDate(new Date());

    boolean updateIpResult = this.updateById(ip);
    if (!updateIpResult) {
      log.error("Update t_ip table failed, try to update the data: {}", ip);
      throw new BusinessException(IpExceptionEnum.IP_UPDATE_ERROR.getCode(),
                                  IpExceptionEnum.IP_UPDATE_ERROR.getMessage());
    }
  }
}




