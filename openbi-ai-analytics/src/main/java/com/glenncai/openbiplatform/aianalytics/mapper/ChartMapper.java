package com.glenncai.openbiplatform.aianalytics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.glenncai.openbiplatform.aianalytics.model.entity.Chart;
import org.springframework.data.repository.query.Param;

/**
 * Chart mapper (dao)
 *
 * @author Glenn Cai
 * @version 1.0 03/09/2023
 */
public interface ChartMapper extends BaseMapper<Chart> {

  /**
   * Get chart list
   *
   * @param page   MyBatis-Plus built-in page
   * @param userId user id
   * @return chart list
   */
  Page<Chart> getChartListVO(@Param("page") Page<Chart> page, long userId);
}




