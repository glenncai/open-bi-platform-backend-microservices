package com.glenncai.openbiplatform.common.utils;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.crypto.digest.MD5;
import com.glenncai.openbiplatform.common.constant.UserConstant;

/**
 * User common utils
 *
 * @author Glenn Cai
 * @version 1.0 20/07/2023
 */
public class UserUtils {

  private UserUtils() {
  }

  public static String encryptPassword(String password) {
    MD5 md5 = MD5.create();
    String hashSalt = md5.digestHex(UserConstant.SALT);
    String hashSaltStart = CharSequenceUtil.subWithLength(hashSalt, 0, 6);
    String hashSaltEnd = CharSequenceUtil.subSuf(hashSalt, hashSalt.length() - 3);
    return md5.digestHex(hashSaltStart + password + hashSaltEnd).toUpperCase();
  }
}
