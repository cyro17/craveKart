package com.cyro.cravekart.controllers;

import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.service.SseEmitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.awt.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SseController {
  private final SseEmitterService sseEmitterService;
  private final AuthContextService authContextService;


  @GetMapping(value = "/notification/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public SseEmitter stream(){
    Long customerId = authContextService.getCustomer().getId();
    return sseEmitterService.register(customerId);
  }

//  @GetMapping(value = "/notification/stream/{orderId}",
//              produces = MediaType.TEXT_EVENT_STREAM_VALUE
//  )
//  public SseEmitter stream(@PathVariable Long orderId){
//    Long customerId = authContextService.getCustomer().getId();
//    return  sseEmitterService.register(orderId);
//
//  }
}
