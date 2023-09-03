package com.glenncai.openbiplatform.user.model.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * User register request body (DTO)
 *
 * @author Glenn Cai
 * @version 1.0 03/09/2023
 */
@Data
public class UserRegisterRequest implements Serializable {

  @Serial
  private static final long serialVersionUID = 5828747689399838674L;

  private String username;

  private String password;

  private String confirmPassword;
}
