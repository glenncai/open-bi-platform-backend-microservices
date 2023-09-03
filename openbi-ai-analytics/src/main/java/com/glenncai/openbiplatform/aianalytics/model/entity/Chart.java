package com.glenncai.openbiplatform.aianalytics.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Table t_chart entity
 *
 * @author Glenn Cai
 * @version 1.0 03/09/2023
 */
@TableName(value = "t_chart")
@Data
public class Chart implements Serializable {

  @Serial
  @TableField(exist = false)
  private static final long serialVersionUID = -4166183602118207671L;

  /**
   * Primary key
   */
  @TableId(type = IdType.ASSIGN_ID)
  private Long id;

  /**
   * Analysis target
   */
  private String goal;

  /**
   * Chart name
   */
  private String name;

  /**
   * Chart data
   */
  private String chartData;

  /**
   * Chart type
   */
  private String chartType;

  /**
   * Generated chart data
   */
  private String genChartData;

  /**
   * Generated chart conclusion
   */
  private String genChartConclusion;

  /**
   * 0: Waiting, 1: Running, 2: Succeed, 3: Failed
   */
  private Integer status;

  /**
   * Execution message
   */
  private String execMessage;

  /**
   * User ID
   */
  private Long userId;

  /**
   * 0: invalid, 1: valid
   */
  @TableLogic
  private Integer valid;

  /**
   * Created time
   */
  private Date createdAt;

  /**
   * Updated time
   */
  private Date updatedAt;
}