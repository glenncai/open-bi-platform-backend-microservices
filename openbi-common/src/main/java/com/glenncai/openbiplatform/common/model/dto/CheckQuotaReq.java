package com.glenncai.openbiplatform.ip.model.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Check quota request body (DTO)
 *
 * @author Glenn Cai
 * @version 1.0 06/09/2023
 */
@Data
public class CheckQuotaReq implements Serializable {

  @Serial
  private static final long serialVersionUID = -1073548262621637885L;

  private Long userId;
}
