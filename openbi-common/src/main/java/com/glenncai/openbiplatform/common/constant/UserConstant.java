package com.glenncai.openbiplatform.common.constant;

/**
 * User constant
 *
 * @author Glenn Cai
 * @version 1.0 19/07/2023
 */
public final class UserConstant {

  /**
   * Encryption salt
   */
  public static final String SALT = "w_yTivHidq5T";

  /**
   * Session map key for login user
   */
  public static final String LOGIN_USER_STAGE = "openbi:user:stage";

  /**
   * Default role
   */
  public static final String DEFAULT_ROLE = "user";

  /**
   * Admin role
   */
  public static final String ADMIN_ROLE = "admin";

  /**
   * Ban role
   */
  public static final String BAN_ROLE = "ban";

  private UserConstant() {
  }
}
