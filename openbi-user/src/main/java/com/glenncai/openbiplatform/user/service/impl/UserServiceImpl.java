package com.glenncai.openbiplatform.user.service.impl;

import static com.glenncai.openbiplatform.common.constant.UserConstant.LOGIN_USER_STAGE;
import static com.glenncai.openbiplatform.common.utils.NetUtils.getClientIpAddress;
import static com.glenncai.openbiplatform.common.utils.UserUtils.encryptPassword;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glenncai.openbiplatform.common.exception.BusinessException;
import com.glenncai.openbiplatform.common.exception.enums.AuthExceptionEnum;
import com.glenncai.openbiplatform.user.mapper.UserMapper;
import com.glenncai.openbiplatform.user.model.dto.CheckUserExistReq;
import com.glenncai.openbiplatform.user.model.dto.UserLoginReq;
import com.glenncai.openbiplatform.user.model.dto.UserRegisterReq;
import com.glenncai.openbiplatform.user.model.entity.User;
import com.glenncai.openbiplatform.user.model.vo.LoginUserVO;
import com.glenncai.openbiplatform.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
   * User login
   *
   * @param userLoginReq user login request body
   * @param request      http request
   * @return filtered user info
   */
  @Override
  public LoginUserVO login(UserLoginReq userLoginReq, HttpServletRequest request) {
    String username = userLoginReq.getUsername();
    String password = userLoginReq.getPassword();
    String clientIp = getClientIpAddress(request);

    if (StringUtils.isAnyBlank(username, password)) {
      throw new BusinessException(AuthExceptionEnum.AUTH_LOGIN_ERROR.getCode(),
                                  AuthExceptionEnum.AUTH_LOGIN_ERROR.getMessage());
    }
    if (username.length() < 4 || username.length() > 16) {
      throw new BusinessException(AuthExceptionEnum.AUTH_LOGIN_ERROR.getCode(),
                                  AuthExceptionEnum.AUTH_LOGIN_ERROR.getMessage());
    }
    if (!username.matches(usernameValidateRegex)) {
      throw new BusinessException(AuthExceptionEnum.AUTH_LOGIN_ERROR.getCode(),
                                  AuthExceptionEnum.AUTH_LOGIN_ERROR.getMessage());
    }
    if (password.contains(" ")) {
      throw new BusinessException(AuthExceptionEnum.AUTH_LOGIN_ERROR.getCode(),
                                  AuthExceptionEnum.AUTH_LOGIN_ERROR.getMessage());
    }
    if (password.length() < 8) {
      throw new BusinessException(AuthExceptionEnum.AUTH_LOGIN_ERROR.getCode(),
                                  AuthExceptionEnum.AUTH_LOGIN_ERROR.getMessage());
    }

    // Encrypt password with MD5
    String encryptedPassword = encryptPassword(password);

    // Check if username and password are correct
    User user = userMapper.login(username, encryptedPassword);

    if (user == null) {
      log.error("Client IP: {}, Username: {}, Error message: Login failed",
                clientIp, username);
      throw new BusinessException(AuthExceptionEnum.AUTH_LOGIN_ERROR.getCode(),
                                  AuthExceptionEnum.AUTH_LOGIN_ERROR.getMessage());
    }

    // Update login ip
    user.setLoginIp(clientIp);
    boolean updateIpResult = this.updateById(user);
    if (!updateIpResult) {
      log.error("Client IP: {}, Username: {}, Error message: Update login ip failed", clientIp,
                username);
      throw new BusinessException(AuthExceptionEnum.AUTH_UPDATE_LOGIN_IP_ERROR.getCode(),
                                  AuthExceptionEnum.AUTH_UPDATE_LOGIN_IP_ERROR.getMessage());
    }

    // Save user info to session after login successfully
    log.info("Client IP: {}, Username: {}, Success message: Login successfully", clientIp,
             username);
    request.getSession().setAttribute(LOGIN_USER_STAGE, user);
    return getCurrentLoginUserVO(user);
  }

  /**
   * User logout
   *
   * @param request http request
   * @return true if logout successfully
   */
  @Override
  public boolean logout(HttpServletRequest request) {
    if (request == null) {
      throw new BusinessException(AuthExceptionEnum.AUTH_EMPTY_HTTP_REQUEST_ERROR.getCode(),
                                  AuthExceptionEnum.AUTH_EMPTY_HTTP_REQUEST_ERROR.getMessage());
    }

    if (request.getSession().getAttribute(LOGIN_USER_STAGE) == null) {
      throw new BusinessException(AuthExceptionEnum.AUTH_OPERATION_ERROR.getCode(),
                                  AuthExceptionEnum.AUTH_OPERATION_ERROR.getMessage());
    }

    // Remove user info from session
    request.getSession().removeAttribute(LOGIN_USER_STAGE);
    return true;
  }

  /**
   * Get current login user's filtered info
   *
   * @param user user entity
   * @return filtered user info
   */
  @Override
  public LoginUserVO getCurrentLoginUserVO(User user) {
    if (user == null) {
      return null;
    }
    LoginUserVO loginUserVO = new LoginUserVO();
    BeanUtils.copyProperties(user, loginUserVO);
    return loginUserVO;
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




