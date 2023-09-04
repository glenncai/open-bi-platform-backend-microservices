package com.glenncai.openbiplatform.user.model.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * User enable request body (DTO)
 *
 * @author Glenn Cai
 * @version 1.0 04/09/2023
 */
@Data
public class UserEnableReq implements Serializable {

  @Serial
  private static final long serialVersionUID = 1777638151990272307L;

  private String username;
}
