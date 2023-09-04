package com.glenncai.openbiplatform.ip.controller;

import com.glenncai.openbiplatform.ip.model.dto.InitIpReq;
import com.glenncai.openbiplatform.ip.service.IpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * IP controller (RESTful API)
 *
 * @author Glenn Cai
 * @version 1.0 04/09/2023
 */
@RestController
@RequestMapping("/ip")
@Slf4j
public class IpController {

  @Resource
  private IpService ipService;

  @PostMapping("/init")
  public void initIp(@RequestBody InitIpReq initIpReq) {
    ipService.initIp(initIpReq);
  }
}
