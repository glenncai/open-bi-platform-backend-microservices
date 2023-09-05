package com.glenncai.openbiplatform.user.controller;

import com.glenncai.openbiplatform.common.annotation.PreAuthorize;
import com.glenncai.openbiplatform.common.common.BaseResponse;
import com.glenncai.openbiplatform.common.common.BaseResult;
import com.glenncai.openbiplatform.common.constant.UserConstant;
import com.glenncai.openbiplatform.user.model.dto.UserAddReq;
import com.glenncai.openbiplatform.user.model.dto.UserDisableReq;
import com.glenncai.openbiplatform.user.model.dto.UserEnableReq;
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
   * @return JWT token
   */
  @PostMapping("/login")
  public BaseResponse<String> login(@RequestBody UserLoginReq userLoginReq,
                                    HttpServletRequest request) {
    String token = userService.login(userLoginReq, request);
    return BaseResult.success(token);
  }

  /**
   * Get current login user filtered info
   *
   * @param request http request
   * @return filtered login user info
   */
  @PostMapping("/get/login")
  @PreAuthorize(anyRole = {UserConstant.DEFAULT_ROLE, UserConstant.ADMIN_ROLE})
  public BaseResponse<LoginUserVO> getCurrentLoginUserInfo(HttpServletRequest request) {
    LoginUserVO result = userService.getCurrentLoginUserInfo(request);
    return BaseResult.success(result);
  }

  /**
   * Add new user
   *
   * @param userAddReq user add request body
   * @param request    http request
   * @return the id of the newly created user
   */
  @PostMapping("/add")
  @PreAuthorize(mustRole = UserConstant.ADMIN_ROLE)
  public BaseResponse<Long> addUser(@RequestBody UserAddReq userAddReq,
                                    HttpServletRequest request) {
    long result = userService.addUser(userAddReq, request);
    return BaseResult.success(result);
  }

  /**
   * Disable user
   *
   * @param userDisableReq user remove request body
   * @param request        http request
   */
  @PostMapping("/disable")
  @PreAuthorize(mustRole = UserConstant.ADMIN_ROLE)
  public void disableUser(@RequestBody UserDisableReq userDisableReq, HttpServletRequest request) {
    userService.disableUser(userDisableReq, request);
  }

  /**
   * Enable user
   *
   * @param userEnableReq user enable request body
   * @param request       http request
   */
  @PostMapping("/enable")
  @PreAuthorize(mustRole = UserConstant.ADMIN_ROLE)
  public void enableUser(@RequestBody UserEnableReq userEnableReq, HttpServletRequest request) {
    userService.enableUser(userEnableReq, request);
  }
}
