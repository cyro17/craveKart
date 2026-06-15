package com.cyro.cravekart.listener;

import com.cravekart.core.events.notification.OrderConfirmedEvent;
import com.cyro.cravekart.config.kafka.KafkaTopicConfiguration;
import com.cyro.cravekart.dto.CustomerDto;
import com.cyro.cravekart.events.order.OrderCreatedEvent;
import com.cyro.cravekart.events.payment.PaymentCancelledEvent;
import com.cyro.cravekart.events.payment.PaymentFailedEvent;
import com.cyro.cravekart.events.payment.PaymentInitiatedEvent;
import com.cyro.cravekart.events.payment.PaymentSuccessEvent;
import com.cyro.cravekart.models.Order;
import com.cyro.cravekart.models.Payment;
import com.cyro.cravekart.models.enums.PaymentProvider;
import com.cyro.cravekart.publishers.OrderEventPublisher;
import com.cyro.cravekart.repository.PaymentRepository;
import com.cyro.cravekart.response.PaymentIntentResult;
import com.cyro.cravekart.service.CustomerService;
import com.cyro.cravekart.service.OrderService;
import com.cyro.cravekart.service.SseEmitterService;
import com.cyro.cravekart.service.StripeService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentListener {

  private final PaymentRepository paymentRepository;
  private final StripeService stripeService;
  private final KafkaTemplate<String, Object> kafkaTemplate;
  private final OrderService orderService;
  private final OrderEventPublisher orderEventPublisher;
  private final CustomerService customerService;
  private final SseEmitterService sseEmitterService;

  @KafkaListener(topics = "order-created", groupId = "payment-service")
  public void handleOrderCreated(OrderCreatedEvent event) throws StripeException {
    log.info("Received order-created event for orderId = {}", event.getOrderId());

    // Idempotency check
    //    if(paymentRepository.existsByOrderId(event.getOrderId())){
    //      log.warn("Payment already exists for orderId = {}", event.getOrderId());
    //      return;
    //    }

    try {
      // create stripe payment intent
      PaymentIntentResult paymentIntentResult =
          stripeService.createPaymentIntent(
              event.getAmount(), event.getCurrency(), event.getOrderId());

      Payment payment =
          Payment.builder()
              .orderId(event.getOrderId())
              .customerId(event.getCustomerId())
              .amount(event.getAmount())
              .currency(event.getCurrency())
              .provider(PaymentProvider.STRIPE)
              .stripePaymentIntentId(paymentIntentResult.getPaymentIntentId())
              .clientSecret(paymentIntentResult.getClientSecret())
              .retryCount(0)
              .build();

      paymentRepository.save(payment);

      log.info("Payment created for orderId = {}", event.getOrderId());

      publishPaymentInitiated(
          event.getOrderId(), event.getCustomerId(), paymentIntentResult.getClientSecret());

    } catch (Exception ex) {
      log.error("Failed to create payment for orderId = {}", ex.getMessage());

      publishPaymentFailed(event.getOrderId(), event.getCustomerId(), "Stripe creation failed");
    }
  }

  @KafkaListener(topics = KafkaTopicConfiguration.PAYMENT_SUCCESS, groupId = "order-service")
  public void handlePaymentSucceeded(PaymentSuccessEvent event) {
    log.info("Payment succeeded for orderId = {}", event.getOrderId());

    Order order = orderService.markAsConfirmed(event.getOrderId());
    CustomerDto customerDto = customerService.getCustomerById(event.getCustomerId());

    OrderConfirmedEvent orderConfirmedEvent =
        OrderConfirmedEvent.builder()
            .orderId(event.getOrderId())
            .customerId(event.getCustomerId())
            .customerName(order.getCustomerName())
            .customerEmail(customerDto.getCustomerEmail())
            .restaurantName(order.getRestaurantName())
            .totalPrice(event.getAmount().toString())
            .estimatedDeliveryTime("30-45 minutes")
            .build();

    orderEventPublisher.publishOrderPaid(orderConfirmedEvent);
  }

  @KafkaListener(topics = KafkaTopicConfiguration.PAYMENT_FAILED, groupId = "order-service")
  public void handlePaymentfailed(PaymentFailedEvent event) {
    log.info("Payment failed for orderId = {}", event.getOrderId());
    orderService.markAsFailed(event.getOrderId());

    sseEmitterService.pushPaymentFailed(
        event.getOrderId(), event.getCustomerId(), event.getReason());
  }

  @KafkaListener(topics = KafkaTopicConfiguration.PAYMENT_CANCELLED, groupId = "order-service")
  public void handlePaymentCancelled(PaymentCancelledEvent event) {
    log.info("Payment cancelled for orderId = {}", event.getOrderId());

    orderService.markAsFailed(event.getOrderId());
    sseEmitterService.pushPaymentFailed(
        event.getOrderId(), event.getCustomerId(), event.getReason());
  }

  // ============== helper ================================
  private void publishPaymentInitiated(Long orderId, Long customerId, String clientSecret) {
    PaymentInitiatedEvent payment =
        PaymentInitiatedEvent.builder()
            .orderId(orderId)
            .customerId(customerId)
            .clientSecret(clientSecret)
            .build();

    kafkaTemplate.send("payment-initiated", orderId.toString(), payment);

    log.info("Payment initiated event for orderId = {}", orderId);
  }

  private void publishPaymentFailed(Long orderId, Long customerId, String message) {
    PaymentFailedEvent failedEvent =
        PaymentFailedEvent.builder()
            .orderId(orderId)
            .customerId(customerId)
            .reason(message)
            .build();

    kafkaTemplate.send("payment-failed", orderId.toString(), failedEvent);
    log.info("Payment failed event for orderId = {}", orderId);
  }
}
