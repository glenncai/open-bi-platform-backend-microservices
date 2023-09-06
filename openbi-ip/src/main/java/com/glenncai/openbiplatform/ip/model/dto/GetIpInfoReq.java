package com.glenncai.openbiplatform.ip.model.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Get ip info request body (DTO)
 *
 * @author Glenn Cai
 * @version 1.0 06/09/2023
 */
@Data
public class GetIpInfoReq implements Serializable {

  @Serial
  private static final long serialVersionUID = 9043570885981572469L;

  private Long userId;
}
