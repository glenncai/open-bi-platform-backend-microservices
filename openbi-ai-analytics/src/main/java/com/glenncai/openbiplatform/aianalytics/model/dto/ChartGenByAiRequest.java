package com.glenncai.openbiplatform.model.dto.chart.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class is for chart generation by AI request body
 *
 * @author Glenn Cai
 * @version 1.0 07/24/2023
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
