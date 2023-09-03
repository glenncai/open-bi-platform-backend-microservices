package com.glenncai.openbiplatform.user.model.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class is for user login request body
 *
 * @author Glenn Cai
 * @version 1.0 19/07/2023
 */
@Data
public class UserLoginReq implements Serializable {

  @Serial
  private static final long serialVersionUID = 372708386515440671L;

  private String username;

  private String password;
}
