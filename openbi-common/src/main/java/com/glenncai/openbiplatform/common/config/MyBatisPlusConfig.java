package com.glenncai.openbiplatform.common.config;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis Plus pagination configuration
 *
 * @author Glenn Cai
 * @version 1.0 26/07/2023
 */
@Configuration
@MapperScan("com.glenncai.openbiplatform.*.mapper")
public class MyBatisPlusConfig {

  /**
   * Pagination interceptor
   *
   * @return mybatis plus interceptor
   */
  @Bean
  public MybatisPlusInterceptor mybatisPlusInterceptor() {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
    return interceptor;
  }
}
