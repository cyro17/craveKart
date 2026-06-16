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
import com.cyro.cravekart.service.impl.OutboxServiceImpl;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
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
  private final OutboxServiceImpl outboxService;

  // ============== Kafka Listeners ================================

  @KafkaListener(
      topics = KafkaTopicConfiguration.ORDER_CREATED,
      groupId = "payment-service",
      containerFactory = "stringKafkaListenerContainerFactory")
  public void handleOrderCreated(ConsumerRecord<String, String> record) {
    log.info("Received order-created event for orderId = {}", event.getOrderId());
    try {
      PaymentIntentResult result = createStripePaymentIntent(event);
      savePayment(event, result);

      outboxService.save(
          "payment-initiated",
          event.getOrderId().toString(),
          "PaymentInitiatedEvent",
          buildPaymentInitiatedEvent(event, result));

    } catch (Exception ex) {
      log.error(
          "Failed to create payment for orderId = {}, reason = {}",
          event.getOrderId(),
          ex.getMessage());
      publishPaymentFailed(event.getOrderId(), event.getCustomerId(), "Stripe creation failed");
    }
  }

  @KafkaListener(topics = KafkaTopicConfiguration.PAYMENT_SUCCESS, groupId = "order-service")
  public void handlePaymentSucceeded(PaymentSuccessEvent event) {
    log.info("Payment succeeded for orderId = {}", event.getOrderId());
    Order order = orderService.markAsConfirmed(event.getOrderId());
    OrderConfirmedEvent confirmedEvent = buildOrderConfirmedEvent(event, order);
    orderEventPublisher.publishOrderPaid(confirmedEvent);
  }

  @KafkaListener(topics = KafkaTopicConfiguration.PAYMENT_FAILED, groupId = "order-service")
  public void handlePaymentFailed(PaymentFailedEvent event) {
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

  // ============== Private Helpers ================================

  private PaymentIntentResult createStripePaymentIntent(OrderCreatedEvent event)
      throws StripeException {
    return stripeService.createPaymentIntent(
        event.getAmount(), event.getCurrency(), event.getOrderId());
  }

  private void savePayment(OrderCreatedEvent event, PaymentIntentResult result) {
    Payment payment =
        Payment.builder()
            .orderId(event.getOrderId())
            .customerId(event.getCustomerId())
            .amount(event.getAmount())
            .currency(event.getCurrency())
            .provider(PaymentProvider.STRIPE)
            .stripePaymentIntentId(result.getPaymentIntentId())
            .clientSecret(result.getClientSecret())
            .retryCount(0)
            .build();

    paymentRepository.save(payment);
    log.info("Payment record saved for orderId = {}", event.getOrderId());
  }

  private OrderConfirmedEvent buildOrderConfirmedEvent(PaymentSuccessEvent event, Order order) {
    CustomerDto customerDto = customerService.getCustomerById(event.getCustomerId());

    return OrderConfirmedEvent.builder()
        .orderId(event.getOrderId())
        .customerId(event.getCustomerId())
        .customerName(order.getCustomerName())
        .customerEmail(customerDto.getCustomerEmail())
        .restaurantName(order.getRestaurantName())
        .totalPrice(event.getAmount().toString())
        .estimatedDeliveryTime("30-45 minutes")
        .build();
  }

  private PaymentInitiatedEvent buildPaymentInitiatedEvent(
      OrderCreatedEvent event, PaymentIntentResult result) {
    return PaymentInitiatedEvent.builder()
        .orderId(event.getOrderId())
        .customerId(event.getCustomerId())
        .clientSecret(result.getClientSecret())
        .build();
  }

  private void publishPaymentFailed(Long orderId, Long customerId, String reason) {
    PaymentFailedEvent event =
        PaymentFailedEvent.builder().orderId(orderId).customerId(customerId).reason(reason).build();

    kafkaTemplate.send("payment-failed", orderId.toString(), event);
    log.info("Payment failed event published for orderId = {}", orderId);
  }
}
