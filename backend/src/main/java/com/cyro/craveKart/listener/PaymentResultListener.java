package com.cyro.cravekart.listener;

import com.cyro.cravekart.config.kafka.KafkaTopicConfiguration;
import com.cyro.cravekart.events.PaymentCancelledEvent;
import com.cyro.cravekart.events.PaymentFailedEvent;
import com.cyro.cravekart.events.PaymentSuccessEvent;
import com.cyro.cravekart.service.SseEmitterService;
import com.cyro.cravekart.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentResultListener {

  private final OrderServiceImpl orderService;
  private final SseEmitterService  sseEmitterService;

  @KafkaListener(
      topics = KafkaTopicConfiguration.PAYMENT_SUCCESS,
      groupId = "order-service"
  )
  public void handlePaymentSucceeded(PaymentSuccessEvent event){
    log.info("Payment succeeded for orderId: {}", event.getOrderId());
    orderService.markAsPaid(event.getOrderId());
    sseEmitterService.pushOrderConfirmed(
        event.getOrderId(), event.getCustomerId()
    );
  }

  @KafkaListener(
      topics = KafkaTopicConfiguration.PAYMENT_FAILED,
      groupId = "order-service"
  )
  public void handlePaymentFailed(PaymentFailedEvent event){
    log.info("Payment failed for orderId: {}", event.getOrderId());
    orderService.markAsFailed(event.getOrderId());
    sseEmitterService.pushPaymentFailed(
        event.getOrderId(), event.getCustomerId(), event.getReason()
    );
  }

  @KafkaListener(
      topics = KafkaTopicConfiguration.PAYMENT_CANCELLED,
      groupId = "order-service"
  )
  public void handlePaymentCancelled(PaymentCancelledEvent event){
    log.info("Payment cancelled for orderId: {}", event.getOrderId());
    orderService.markAsFailed(event.getOrderId());
    sseEmitterService.pushPaymentFailed(
        event.getOrderId(), event.getCustomerId(), event.getReason()
    );
  }
}
