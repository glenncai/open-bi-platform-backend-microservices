package com.glenncai.openbiplatform.user.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * User filtered view object
 *
 * @author Glenn Cai
 * @version 1.0 06/09/2023
 */
@Data
public class UserVO implements Serializable {

  @Serial
  private static final long serialVersionUID = 908959326105763541L;

  /**
   * User id
   */
  private Long id;

  /**
   * Username
   */
  private String username;

  /**
   * User role
   */
  private String role;

  /**
   * Login IP
   */
  private String loginIp;

  /**
   * Is valid
   */
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
