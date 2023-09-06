package com.glenncai.openbiplatform.aianalytics.model.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Chart generation by AI request body
 *
 * @author Glenn Cai
 * @version 1.0 24/07/2023
 */
@Data
public class ChartGenByAiRequest implements Serializable {

  @Serial
  private static final long serialVersionUID = -1877460408831802608L;

  /**
   * Chart name
   */
  private String name;

  /**
   * Goal
   */
  private String goal;

  /**
   * Chart type
   */
  private String chartType;
}
