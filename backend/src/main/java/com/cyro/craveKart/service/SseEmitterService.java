package com.cyro.cravekart.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseEmitterService {
  public SseEmitter register(Long orderId);

  public void pushClientSecret(Long orderId,
                               Long customerId,
                               String clientSecret);

  public void pushOrderConfirmed(Long orderId, Long customerId);
  public void pushPaymentFailed(Long orderId, Long customerId, String reason);

}
