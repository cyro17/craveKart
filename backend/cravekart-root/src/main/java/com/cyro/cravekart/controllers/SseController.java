package com.cyro.cravekart.controllers;

import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.service.SseEmitterService;
import java.awt.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SseController {
  private final SseEmitterService sseEmitterService;
  private final AuthContextService authContextService;

  @GetMapping(value = "/notification/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public SseEmitter stream() {
    Long customerId = authContextService.getCustomer().getId();
    return sseEmitterService.register(customerId);
  }

  @Secured("ROLE_RESTAURANT_PARTNER")
  @GetMapping(value = "/restaurant/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public SseEmitter restaurantStream() {
    Long restaurantId = authContextService.getRestaurantPartner().getRestaurant().getId();
    return sseEmitterService.registerRestaurant(restaurantId);
  }
}
