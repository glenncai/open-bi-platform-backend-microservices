package com.glenncai.openbiplatform.aianalytics.model.dto;

import com.glenncai.openbiplatform.aianalytics.model.enums.ChartStatusEnum;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Updates chart status request body
 *
 * @author Glenn Cai
 * @version 1.0 8/2/2023
 */
@Data
public class ChartUpdateStatusRequest implements Serializable {

  @Serial
  private static final long serialVersionUID = 674532068005804601L;

  /**
   * Chart id
   */
  private Long id;

  /**
   * Chart status
   */
  private ChartStatusEnum status;
}
