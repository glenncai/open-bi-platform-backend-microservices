package com.glenncai.openbiplatform.model.enums;

import static com.glenncai.openbiplatform.constant.UserConstant.ADMIN_ROLE;
import static com.glenncai.openbiplatform.constant.UserConstant.BAN_ROLE;
import static com.glenncai.openbiplatform.constant.UserConstant.DEFAULT_ROLE;

import java.util.Arrays;
import java.util.List;

/**
 * User role enum
 *
 * @author Glenn Cai
 * @version 1.0 07/23/2023
 */
public enum UserRoleEnum {

  USER("User", DEFAULT_ROLE),
  ADMIN("Administrator", ADMIN_ROLE),
  BAN("Ban", BAN_ROLE);

  private final String text;
  private final String value;

  UserRoleEnum(String text, String value) {
    this.text = text;
    this.value = value;
  }

  /**
   * Get all values
   *
   * @return all role values
   */
  public static List<String> getValues() {
    return Arrays.stream(UserRoleEnum.values()).map(UserRoleEnum::getValue).toList();
  }

  /**
   * Get enum by value
   *
   * @param value role value
   * @return role enum
   */
  public static UserRoleEnum getEnumByValue(String value) {
    return Arrays.stream(UserRoleEnum.values())
                 .filter(roleEnum -> roleEnum.getValue().equals(value))
                 .findFirst()
                 .orElse(null);
  }

  public String getText() {
    return text;
  }

  public String getValue() {
    return value;
  }
}
