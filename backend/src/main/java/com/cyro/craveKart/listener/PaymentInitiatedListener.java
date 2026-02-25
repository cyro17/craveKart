package com.cyro.cravekart.listener;

import com.cyro.cravekart.events.PaymentInitiatedEvent;
import com.cyro.cravekart.service.SseEmitterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentInitiatedListener {
  private final SseEmitterService sseEmitterService;

  @KafkaListener(topics = "payment-initiated",
      groupId = "notification-service")
  public void handlePaymentInitiated(PaymentInitiatedEvent paymentInitiatedEvent) {
    log.info("Received Payment Initiated event for orderId = {}", paymentInitiatedEvent.getOrderId());

    sseEmitterService.pushClientSecret(
        paymentInitiatedEvent.getCustomerId(),
        paymentInitiatedEvent.getOrderId(),
        paymentInitiatedEvent.getClientSecret()
    );
  }

}
