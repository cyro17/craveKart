package com.cyro.cravekart.service;

import com.cyro.cravekart.models.enums.OrderStatus;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseEmitterService {
  public SseEmitter register(Long orderId);

  public void pushClientSecret(Long orderId, Long customerId, String clientSecret);

  //  public void pushOrderConfirmed(Long orderId, Long customerId);
  public void pushPaymentReceived(Long customerId, Long orderId);

  public void pushOrderStatusUpdate(
      Long customerId, Long orderId, OrderStatus orderStatus, String message);

  public void pushPaymentFailed(Long orderId, Long customerId, String reason);

  //   Restaurant Channel
  public SseEmitter registerRestaurant(Long restaurantId);

  void pushNewOrder(Long restaurantId, Long orderId, String customerName, String totalPrice);
}
