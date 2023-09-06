package com.glenncai.openbiplatform.common.model.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Reduce call quota request body (DTO)
 *
 * @author Glenn Cai
 * @version 1.0 06/09/2023
 */
@Data
public class ReduceCallQuotaReq implements Serializable {

  @Serial
  private static final long serialVersionUID = -3377820882284659897L;

  private Long userId;
}
