package com.glenncai.openbiplatform.ip.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Table t_ip entity
 *
 * @author Glenn Cai
 * @version 1.0 03/09/2023
 */
@TableName(value = "t_ip")
@Data
public class Ip implements Serializable {

  @Serial
  @TableField(exist = false)
  private static final long serialVersionUID = -8984792353958244073L;

  /**
   * IP
   */
  @TableId(type = IdType.INPUT)
  private String ip;

  /**
   * Remaining AI service quota of this IP today
   */
  private Integer remainingQuota;

  /**
   * Last call API service date
   */
  private Date lastCallDate;
}