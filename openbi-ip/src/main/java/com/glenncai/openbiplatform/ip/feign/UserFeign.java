package com.glenncai.openbiplatform.ip.feign;

import com.glenncai.openbiplatform.common.common.BaseResponse;
import com.glenncai.openbiplatform.common.model.vo.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * User service feign
 *
 * @author Glenn Cai
 * @version 1.0 6/9/2023
 */
@FeignClient(name = "OPEN-BI-USER-SERVICE/api/user")
public interface UserFeign {

  /**
   * Get user info by user id
   *
   * @param userId user id
   * @return user filtered info
   */
  @PostMapping("/get/{userId}")
  BaseResponse<UserVO> getUserInfoByUserId(@PathVariable("userId") Long userId);
}
