package com.glenncai.openbiplatform.aianalytics.feign;

import com.glenncai.openbiplatform.common.common.BaseResponse;
import com.glenncai.openbiplatform.common.model.vo.LoginUserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * User service feign
 *
 * @author Glenn Cai
 * @version 1.0 06/09/2023
 */
@FeignClient(name = "OPEN-BI-USER-SERVICE/api/user")
public interface UserFeign {

  /**
   * Get current login user filtered info
   *
   * @param request http request
   * @return filtered login user info
   */
  @PostMapping("/get/login")
  BaseResponse<LoginUserVO> getCurrentLoginUserInfo(HttpServletRequest request);
}
