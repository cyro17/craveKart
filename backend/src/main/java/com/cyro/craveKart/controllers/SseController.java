package com.cyro.cravekart.controllers;

import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.service.SseEmitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.awt.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notification")
public class SseController {
  private final SseEmitterService sseEmitterService;
  private final AuthContextService authContextService;

  @GetMapping(value = "/stream",
              produces = MediaType.TEXT_EVENT_STREAM_VALUE
  )
  public SseEmitter stream(){
    Long customerId = authContextService.getCustomer().getId();
    return  sseEmitterService.register(customerId);

  }
}
