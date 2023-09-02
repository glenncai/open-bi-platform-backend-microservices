package com.glenncai.openbiplatform.user.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Table t_user entity
 *
 * @author Glenn Cai
 * @version 1.0 03/09/2023
 */
@TableName(value = "t_user")
@Data
public class User implements Serializable {

  @Serial
  @TableField(exist = false)
  private static final long serialVersionUID = 2530882526664392060L;

  /**
   * Primary key
   */
  @TableId(type = IdType.ASSIGN_ID)
  private Long id;

  /**
   * Username
   */
  private String username;

  /**
   * Password
   */
  private String password;

  /**
   * user / admin / ban
   */
  private String role;

  /**
   * Login IP
   */
  private String loginIp;

  /**
   * 0: invalid, 1: valid
   */
  @TableLogic
  private Integer valid;

  /**
   * Created time
   */
  private Date createdAt;

  /**
   * Updated time
   */
  private Date updatedAt;
}