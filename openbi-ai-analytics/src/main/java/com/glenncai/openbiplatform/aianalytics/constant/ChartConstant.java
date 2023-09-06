package com.glenncai.openbiplatform.aianalytics.constant;

import java.util.Arrays;
import java.util.List;

/**
 * Chart constant
 *
 * @author Glenn Cai
 * @version 1.0 07/28/2023
 */
public class ChartConstant {

  /**
   * AI generated chart conclusion delimiter
   */
  public static final String CHART_CONCLUSION_DELIMITER = "《《《《《《";

  /**
   * AI generated chart conclusion split length
   */
  public static final int CHART_CONCLUSION_SPLIT_LENGTH = 3;

  /**
   * File max size is 1MB
   */
  public static final int FILE_MAX_SIZE = 1;

  /**
   * Valid file extensions
   */
  public static final List<String> VALID_FILE_EXTENSIONS = Arrays.asList("xls", "xlsx");

  private ChartConstant() {
  }
}
