package com.glenncai.openbiplatform.aianalytics.utils;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Chart utils
 *
 * @author Glenn Cai
 * @version 1.0 02/08/2023
 */
public class ChartUtils {

  private ChartUtils() {
  }

  /**
   * Generate chart name automatically
   *
   * @param inputChartName   user input chart name
   * @param originalFileName original file name
   * @return chart name
   */
  public static String genChartNameAuto(String inputChartName, String originalFileName) {
    String baseFilename = FilenameUtils.getBaseName(originalFileName);
    return StringUtils.isBlank(inputChartName) ? baseFilename : inputChartName;
  }
}
