package com.glenncai.openbiplatform.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.glenncai.openbiplatform.user.model.entity.User;

/**
 * User mapper (DAO)
 *
 * @author Glenn Cai
 * @version 1.0 03/09/2023
 */
public interface UserMapper extends BaseMapper<User> {

  /**
   * Custom login sql query
   *
   * @param username username
   * @param password password
   * @return user info
   */
  User login(String username, String password);

  /**
   * Enable user by id (update valid to 1)
   *
   * @param id user id
   */
  void enableUser(Long id);

  /**
   * Select disabled user by username
   *
   * @param username username
   * @return user info
   */
  User selectDisabledUserByUsername(String username);
}




