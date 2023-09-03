package com.glenncai.openbiplatform.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glenncai.openbiplatform.user.model.dto.CheckUserExistReq;
import com.glenncai.openbiplatform.user.model.dto.UserRegisterReq;
import com.glenncai.openbiplatform.user.model.entity.User;

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
   * Check if user exist
   *
   * @param checkUserExistReq check user exist request body
   */
  void checkUserExist(CheckUserExistReq checkUserExistReq);
}
