package com.glenncai.openbiplatform.user.model.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * User remove request body (DTO)
 *
 * @author Glenn Cai
 * @version 1.0 04/09/2023
 */
@Data
public class UserDisableReq implements Serializable {

  @Serial
  private static final long serialVersionUID = -3800607178412473883L;

  private String username;
}
