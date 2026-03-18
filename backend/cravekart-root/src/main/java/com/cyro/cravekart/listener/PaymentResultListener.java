package com.cyro.cravekart.listener;

import com.cravekart.core.events.notification.OrderConfirmedEvent;
import com.cyro.cravekart.config.kafka.KafkaTopicConfiguration;
import com.cyro.cravekart.dto.CustomerDto;
import com.cyro.cravekart.events.payment.PaymentCancelledEvent;
import com.cyro.cravekart.events.payment.PaymentFailedEvent;
import com.cyro.cravekart.events.payment.PaymentSuccessEvent;
import com.cyro.cravekart.publishers.OrderEventPublisher;
import com.cyro.cravekart.service.CustomerService;
import com.cyro.cravekart.service.SseEmitterService;
import com.cyro.cravekart.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentResultListener {

  private final OrderServiceImpl orderService;
  private final SseEmitterService sseEmitterService;
  private final KafkaTemplate<String, Object> kafkaTemplate;
  private final CustomerService customerService;
  private final OrderEventPublisher orderEventPublisher;

  @KafkaListener(topics = KafkaTopicConfiguration.PAYMENT_SUCCESS, groupId = "order-service")
  public void handlePaymentSucceeded(PaymentSuccessEvent event) {
    log.info("Payment succeeded for orderId: {}", event.getOrderId());
    orderService.markAsConfirmed(event.getOrderId());

    CustomerDto customer = customerService.getMyProfile();
    // send out event to notification service to send email / notification
    OrderConfirmedEvent orderConfirmedEvent =
        OrderConfirmedEvent.builder()
            .orderId(event.getOrderId())
            .customerId(event.getCustomerId())
            .customerName(customer.getCustomerName())
            .customerEmail(customer.getCustomerEmail())
            .totalPrice(event.getAmount().toString())
            .estimatedDeliveryTime("30-45 minutes")
            .build();
    orderEventPublisher.publishOrderPaid(orderConfirmedEvent);
  }

  @KafkaListener(topics = KafkaTopicConfiguration.PAYMENT_FAILED, groupId = "order-service")
  public void handlePaymentFailed(PaymentFailedEvent event) {
    log.info("Payment failed for orderId: {}", event.getOrderId());
    orderService.markAsFailed(event.getOrderId());
    sseEmitterService.pushPaymentFailed(
        event.getOrderId(), event.getCustomerId(), event.getReason());
  }

  @KafkaListener(topics = KafkaTopicConfiguration.PAYMENT_CANCELLED, groupId = "order-service")
  public void handlePaymentCancelled(PaymentCancelledEvent event) {
    log.info("Payment cancelled for orderId: {}", event.getOrderId());
    orderService.markAsFailed(event.getOrderId());
    sseEmitterService.pushPaymentFailed(
        event.getOrderId(), event.getCustomerId(), event.getReason());
  }
}
