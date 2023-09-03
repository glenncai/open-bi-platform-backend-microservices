package com.glenncai.openbiplatform.user.service.impl;

import static com.glenncai.openbiplatform.common.utils.NetUtils.getClientIpAddress;
import static com.glenncai.openbiplatform.common.utils.UserUtils.encryptPassword;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glenncai.openbiplatform.common.exception.BusinessException;
import com.glenncai.openbiplatform.common.exception.enums.AuthExceptionEnum;
import com.glenncai.openbiplatform.user.mapper.UserMapper;
import com.glenncai.openbiplatform.user.model.dto.CheckUserExistReq;
import com.glenncai.openbiplatform.user.model.dto.UserRegisterReq;
import com.glenncai.openbiplatform.user.model.entity.User;
import com.glenncai.openbiplatform.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * User service implementation
 *
 * @author Glenn Cai
 * @version 1.0 03/09/2023
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

  private static final String usernameValidateRegex = "^[a-zA-Z0-9]{4,16}$";

  @Resource
  private UserMapper userMapper;

  /**
   * User register
   *
   * @param userRegisterReq user register request body
   * @param request         http request
   * @return the id of the newly created user
   */
  @Override
  public long register(UserRegisterReq userRegisterReq, HttpServletRequest request) {
    String username = userRegisterReq.getUsername();
    String password = userRegisterReq.getPassword();
    String confirmPassword = userRegisterReq.getConfirmPassword();
    String clientIp = getClientIpAddress(request);

    if (StringUtils.isAnyBlank(username, password, confirmPassword)) {
      throw new BusinessException(AuthExceptionEnum.AUTH_EMPTY_ERROR.getCode(),
                                  AuthExceptionEnum.AUTH_EMPTY_ERROR.getMessage());
    }
    if (username.length() < 4 || username.length() > 16) {
      throw new BusinessException(AuthExceptionEnum.AUTH_USERNAME_LENGTH_ERROR.getCode(),
                                  AuthExceptionEnum.AUTH_USERNAME_LENGTH_ERROR.getMessage());
    }
    if (!username.matches(usernameValidateRegex)) {
      throw new BusinessException(AuthExceptionEnum.AUTH_USERNAME_FORMAT_ERROR.getCode(),
                                  AuthExceptionEnum.AUTH_USERNAME_FORMAT_ERROR.getMessage());
    }
    if (password.contains(" ")) {
      throw new BusinessException(AuthExceptionEnum.AUTH_PASSWORD_SPACE_ERROR.getCode(),
                                  AuthExceptionEnum.AUTH_PASSWORD_SPACE_ERROR.getMessage());
    }
    if (password.length() < 8) {
      throw new BusinessException(AuthExceptionEnum.AUTH_PASSWORD_LENGTH_ERROR.getCode(),
                                  AuthExceptionEnum.AUTH_PASSWORD_LENGTH_ERROR.getMessage());
    }
    if (!password.equals(confirmPassword)) {
      throw new BusinessException(AuthExceptionEnum.AUTH_PASSWORD_NOT_MATCH_ERROR.getCode(),
                                  AuthExceptionEnum.AUTH_PASSWORD_NOT_MATCH_ERROR.getMessage());
    }

    synchronized (username.intern()) {
      // Check if username already exists
      CheckUserExistReq checkUserExistReq = new CheckUserExistReq();
      checkUserExistReq.setUsername(username);
      checkUserExist(checkUserExistReq);

      // Encrypt password with MD5
      String encryptedPassword = encryptPassword(password);

      // Create user
      User user = new User();
      user.setUsername(username);
      user.setPassword(encryptedPassword);
      boolean createResult = this.save(user);
      if (!createResult) {
        log.error("Client IP: {}, Username: {}, Error message: Failed to create new user", clientIp,
                  username);
        throw new BusinessException(AuthExceptionEnum.AUTH_CREATE_USER_ERROR.getCode(),
                                    AuthExceptionEnum.AUTH_CREATE_USER_ERROR.getMessage());
      }

      log.info("Client IP: {}, Username: {}, Success message: Create user successfully", clientIp,
               username);
      return user.getId();
    }
  }

  /**
   * Check if user exist
   *
   * @param checkUserExistReq check user exist request body
   */
  @Override
  public void checkUserExist(CheckUserExistReq checkUserExistReq) {
    User user = this.lambdaQuery().eq(User::getUsername, checkUserExistReq.getUsername()).one();
    if (user == null) {
      throw new BusinessException(AuthExceptionEnum.AUTH_USERNAME_EXIST_ERROR.getCode(),
                                  AuthExceptionEnum.AUTH_USERNAME_EXIST_ERROR.getMessage());
    }
  }
}




