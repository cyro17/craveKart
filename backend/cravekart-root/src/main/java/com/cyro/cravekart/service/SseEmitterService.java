package com.cyro.cravekart.service;

import com.cyro.cravekart.models.enums.OrderStatus;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface SseEmitterService {
  public SseEmitter register(Long orderId);

  public void pushClientSecret(Long orderId,
                               Long customerId,
                               String clientSecret);

  public void pushOrderConfirmed(Long orderId, Long customerId);
  public void pushPaymentFailed(Long orderId, Long customerId, String reason);
  public void pushOrderStatusUpdate(Long customerId,
                                    Long orderId,
                                    OrderStatus orderStatus,
                                    String message);


}
