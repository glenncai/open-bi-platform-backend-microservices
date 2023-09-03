package com.glenncai.openbiplatform.aikey.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Table t_ai_key entity
 *
 * @author Glenn Cai
 * @version 1.0 03/09/2023
 */
@TableName(value = "t_ai_key")
@Data
public class AiKey implements Serializable {

  @Serial
  @TableField(exist = false)
  private static final long serialVersionUID = 3576199743618758013L;

  /**
   * Primary key
   */
  @TableId(type = IdType.ASSIGN_ID)
  private Long id;

  /**
   * AI key
   */
  private String key;

  /**
   * user / admin
   */
  private String belongTo;

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