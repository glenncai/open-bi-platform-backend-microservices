package com.glenncai.openbiplatform.user.controller;

import com.glenncai.openbiplatform.common.common.BaseResponse;
import com.glenncai.openbiplatform.common.common.BaseResult;
import com.glenncai.openbiplatform.user.model.dto.UserLoginReq;
import com.glenncai.openbiplatform.user.model.dto.UserRegisterReq;
import com.glenncai.openbiplatform.user.model.vo.LoginUserVO;
import com.glenncai.openbiplatform.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * User controller (RESTful API)
 *
 * @author Glenn Cai
 * @version 1.0 04/09/2023
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

  @Resource
  private UserService userService;

  /**
   * User register api
   *
   * @param userRegisterReq user register request body
   * @param request         http request
   * @return the id of the newly created user
   */
  @PostMapping("/register")
  public BaseResponse<Long> register(@RequestBody UserRegisterReq userRegisterReq,
                                     HttpServletRequest request) {
    long result = userService.register(userRegisterReq, request);
    return BaseResult.success(result);
  }

  /**
   * User login api
   *
   * @param userLoginReq user login request body
   * @param request      http request
   * @return filtered user info
   */
  @PostMapping("/login")
  public BaseResponse<LoginUserVO> login(@RequestBody UserLoginReq userLoginReq,
                                         HttpServletRequest request) {
    LoginUserVO result = userService.login(userLoginReq, request);
    return BaseResult.success(result);
  }

  /**
   * User logout api
   *
   * @param request http request
   * @return success message
   */
  @PostMapping("/logout")
  public BaseResponse<Boolean> logout(HttpServletRequest request) {
    return BaseResult.success(userService.logout(request));
  }

  /**
   * Get current login user filtered info
   *
   * @param request http request
   * @return filtered login user info
   */
  @PostMapping("/get/login")
  public BaseResponse<LoginUserVO> getCurrentLoginUserInfo(HttpServletRequest request) {
    LoginUserVO result = userService.getCurrentLoginUserInfo(request);
    return BaseResult.success(result);
  }
}
