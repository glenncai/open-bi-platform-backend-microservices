package com.glenncai.openbiplatform.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Authorization annotation
 *
 * @author Glenn Cai
 * @version 1.0 03/09/2023
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PreAuthorize {

  /**
   * Any role allowed
   *
   * @return role list
   */
  String[] anyRole() default "";

  /**
   * Must role allowed
   *
   * @return role list
   */
  String mustRole() default "";
}
