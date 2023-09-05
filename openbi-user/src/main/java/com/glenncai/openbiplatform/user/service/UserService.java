package com.glenncai.openbiplatform.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glenncai.openbiplatform.user.model.dto.CheckUserExistReq;
import com.glenncai.openbiplatform.user.model.dto.UserAddReq;
import com.glenncai.openbiplatform.user.model.dto.UserDisableReq;
import com.glenncai.openbiplatform.user.model.dto.UserEnableReq;
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
  String login(UserLoginReq userLoginReq, HttpServletRequest request);

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
   * Check if user exist for register / add user
   *
   * @param checkUserExistReq check user exist request body
   */
  void checkUserExist(CheckUserExistReq checkUserExistReq);

  /**
   * Add new user
   *
   * @param userAddReq user add request body
   * @param request    http request
   * @return the id of the newly created user
   */
  long addUser(UserAddReq userAddReq, HttpServletRequest request);

  /**
   * Disable user
   *
   * @param userDisableReq user remove request body
   * @param request        http request
   */
  void disableUser(UserDisableReq userDisableReq, HttpServletRequest request);

  /**
   * Enable user
   *
   * @param userEnableReq user enable request body
   * @param request       http request
   */
  void enableUser(UserEnableReq userEnableReq, HttpServletRequest request);
}
