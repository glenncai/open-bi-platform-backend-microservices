package com.glenncai.openbiplatform.user.model.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Check user exist request body (DTO)
 *
 * @author Glenn Cai
 * @version 1.0 4/9/2023
 */
@Data
public class CheckUserExistReq implements Serializable {

  @Serial
  private static final long serialVersionUID = -771335098352319286L;

  private String username;
}
