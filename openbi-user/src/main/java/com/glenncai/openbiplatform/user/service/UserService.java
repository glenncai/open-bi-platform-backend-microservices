package com.glenncai.openbiplatform.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glenncai.openbiplatform.user.model.dto.CheckUserExistReq;
import com.glenncai.openbiplatform.user.model.dto.UserLoginReq;
import com.glenncai.openbiplatform.user.model.dto.UserRegisterReq;
import com.glenncai.openbiplatform.user.model.entity.User;
import com.glenncai.openbiplatform.user.model.vo.LoginUserVO;

import javax.servlet.http.HttpServletRequest;

/**
 * User service
 *
 * @author Glenn Cai
 * @version 1.0 03/09/2023
 */
public interface UserService extends IService<User> {

  /**
   * User register
   *
   * @param userRegisterReq user register request body
   * @param request         http request
   * @return the id of the newly created user
   */
  long register(UserRegisterReq userRegisterReq, HttpServletRequest request);

  /**
   * User login
   *
   * @param userLoginReq user login request body
   * @param request      http request
   * @return filtered user info
   */
  LoginUserVO login(UserLoginReq userLoginReq, HttpServletRequest request);

  /**
   * User logout
   *
   * @param request http request
   * @return true if logout successfully
   */
  boolean logout(HttpServletRequest request);

  /**
   * Get login user's filtered info
   *
   * @param user user entity
   * @return filtered user info
   */
  LoginUserVO getLoginUserVO(User user);

  /**
   * Get current login user filtered info
   *
   * @param request http request
   * @return filtered user info
   */
  LoginUserVO getCurrentLoginUserInfo(HttpServletRequest request);

  /**
   * Check if user exist
   *
   * @param checkUserExistReq check user exist request body
   */
  void checkUserExist(CheckUserExistReq checkUserExistReq);
}
