package com.glenncai.openbiplatform.user.model.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * User add request body (DTO)
 *
 * @author Glenn Cai
 * @version 1.0 04/09/2023
 */
@Data
public class UserAddReq implements Serializable {

  @Serial
  private static final long serialVersionUID = 521605385260627531L;

  private String username;

  private String password;

  private String role;
}
