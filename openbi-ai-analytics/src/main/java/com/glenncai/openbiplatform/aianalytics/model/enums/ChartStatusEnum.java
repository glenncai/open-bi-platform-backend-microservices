package com.glenncai.openbiplatform.aianalytics.model.enums;

import java.util.Arrays;
import java.util.List;

/**
 * Chart status enum
 *
 * @author Glenn Cai
 * @version 1.0 07/28/2023
 */
public enum ChartStatusEnum {

  WAITING("Waiting", 0),
  RUNNING("Running", 1),
  SUCCEED("Succeed", 2),
  FAILED("Failed", 3);

  private final String text;
  private final int value;

  ChartStatusEnum(String text, int value) {
    this.text = text;
    this.value = value;
  }

  /**
   * Get all values
   *
   * @return all chart status values
   */
  public static List<Integer> getValues() {
    return Arrays.stream(ChartStatusEnum.values()).map(ChartStatusEnum::getValue).toList();
  }

  /**
   * Get enum text by value
   *
   * @param value status value
   * @return chart status enum text
   */
  public static ChartStatusEnum getEnumTextByValue(int value) {
    return Arrays.stream(ChartStatusEnum.values())
                 .filter(chartStatusEnum -> chartStatusEnum.getValue() == value)
                 .findFirst()
                 .orElse(null);
  }

  public String getText() {
    return text;
  }

  public int getValue() {
    return value;
  }
}
