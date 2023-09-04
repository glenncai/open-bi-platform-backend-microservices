package com.glenncai.openbiplatform.ip.model.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Init ip request body (DTO)
 *
 * @author Glenn Cai
 * @version 1.0 04/09/2023
 */
@Data
public class InitIpReq implements Serializable {

  @Serial
  private static final long serialVersionUID = 4946753598053714651L;

  private String ip;
}
