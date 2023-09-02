package com.glenncai.openbiplatform.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is for xxx.
 *
 * @author Glenn Cai
 * @version 1.0 1/9/2023
 */
@RestController
public class TestController {

  @GetMapping("/test")
  public String test() {
    return "test";
  }
}
