package com.glenncai.openbiplatform.aianalytics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AiAnalyticsApplicationTests {

  @Test
  void contextLoads() {
    String text = "Hello World!";
    Assertions.assertEquals("Hello World!", text);
  }

}
