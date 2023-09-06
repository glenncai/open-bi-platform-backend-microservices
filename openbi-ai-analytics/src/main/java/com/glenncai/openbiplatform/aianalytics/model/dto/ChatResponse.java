package com.glenncai.openbiplatform.aianalytics.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * AI chat response body
 *
 * @author Glenn Cai
 * @version 1.0 28/07/2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse implements Serializable {

  @Serial
  private static final long serialVersionUID = 2041802120427951377L;

  /**
   * Response data content
   */
  private String content;
}
