package com.cyro.cravekart.listener;

import com.cyro.cravekart.events.OrderCreatedEvent;
import com.cyro.cravekart.events.PaymentFailedEvent;
import com.cyro.cravekart.events.PaymentInitiatedEvent;
import com.cyro.cravekart.models.Payment;
import com.cyro.cravekart.models.enums.PaymentProvider;
import com.cyro.cravekart.models.enums.PaymentStatus;
import com.cyro.cravekart.repository.PaymentRepository;
import com.cyro.cravekart.response.PaymentIntentResult;
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
  private final KafkaTemplate<String, Object>  kafkaTemplate;


  @KafkaListener(topics = "order-created", groupId = "payment-service" )
  public void handleOrderCreated(OrderCreatedEvent event) throws StripeException {
    log.info("Received order-created event for orderId = {}", event.getOrderId());

    // Idempotency check
    if(paymentRepository.existsByOrderId(event.getOrderId())){
      log.warn("Payment already exists for orderId = {}", event.getOrderId());
      return;
    }

    try {
      // create stripe payment intent
      PaymentIntentResult paymentIntentResult = stripeService.createPaymentIntent(
          event.getAmount(),
          event.getCurrency(),
          event.getOrderId()
      );


      Payment payment = Payment.builder()
          .orderId(event.getOrderId())
          .customerId(event.getCustomerId())
          .amount(event.getAmount())
          .currency(event.getCurrency())
          .provider(PaymentProvider.STRIPE)
          .stripePaymentIntentId(paymentIntentResult.getPaymentIntentId())
          .clientSecret(paymentIntentResult.getClientSecret())
          .status(PaymentStatus.PENDING)
          .retryCount(0)
          .build();

      paymentRepository.save(payment);

      log.info("Payment created for orderId = {}", event.getOrderId());

      publishPaymentInitiated(
          event.getOrderId(),
          event.getCustomerId(),
          paymentIntentResult.getClientSecret()
      );


    } catch (Exception ex){
      log.info("Failed to create payment for orderId = {}", event.getOrderId());
      publishPaymentFailed(event.getOrderId(),
          event.getCustomerId(),
          "Stripe creation failed");
      throw ex;
    }
  }

  // ============== helper ================================
  private void publishPaymentInitiated(Long orderId,
                                       Long customerId,
                                       String clientSecret){
    PaymentInitiatedEvent payment = PaymentInitiatedEvent.builder()
        .orderId(orderId)
        .customerId(customerId)
        .clientSecret(clientSecret)
        .build();

    kafkaTemplate.send(
        "payment-initiated",
        orderId.toString(),
        payment);

    log.info("Payment initiated event for orderId = {}", orderId);
  }

  private void publishPaymentFailed(Long orderId, Long customerId,  String message) {
    PaymentFailedEvent failedEvent = PaymentFailedEvent.builder()
        .orderId(orderId)
        .customerId(customerId)
        .reason(message).build();

    kafkaTemplate.send("payment-failed", orderId.toString(),  failedEvent);
    log.info("Payment failed event for orderId = {}", orderId);
  }

}
