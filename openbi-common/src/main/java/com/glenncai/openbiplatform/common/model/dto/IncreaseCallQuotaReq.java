package com.glenncai.openbiplatform.common.model.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Increase call quota request body (DTO)
 *
 * @author Glenn Cai
 * @version 1.0 06/09/2023
 */
@Data
public class IncreaseCallQuotaReq implements Serializable {

  @Serial
  private static final long serialVersionUID = 9003784773871691173L;

  private Long userId;
}
