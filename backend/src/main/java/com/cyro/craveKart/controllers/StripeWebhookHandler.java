package com.cyro.cravekart.controllers;

import com.cyro.cravekart.config.kafka.KafkaTopicConfiguration;
import com.cyro.cravekart.events.PaymentFailedEvent;
import com.cyro.cravekart.events.PaymentSuccessEvent;
import com.cyro.cravekart.models.Payment;
import com.cyro.cravekart.models.enums.PaymentStatus;
import com.cyro.cravekart.repository.PaymentRepository;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/webhook")
@RequiredArgsConstructor
@Slf4j
public class StripeWebhookHandler {

  @Value("${stripe.webhook-secret}")
  private String webhookSecret;
  private final KafkaTemplate<String, Object> kafkaTemplate;
  private final PaymentRepository paymentRepository;

  @PostMapping(value = "/stripe", consumes = "application/json")
  public ResponseEntity<String> handleWebhook(
      @RequestBody String payload,
      @RequestHeader("Stripe-Signature") String sigHeader
  ){
    Event event;
    try {
      event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

    } catch (Exception e) {
      log.error("Invalid webhook signature: {}", e.getMessage());
      return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
    }

    PaymentIntent intent;
    try {
      intent = (PaymentIntent) event.getDataObjectDeserializer()
          .getObject().orElseThrow();

    } catch (Exception e) {
      return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid intent");
    }

    if(event.getId() != null && paymentRepository.existsByStripeEventId(event.getId())) {
      log.warn("Duplicate payment id for event {}", event.getId());
      return  ResponseEntity.status(HttpStatus.OK).body("Already processed");
    }

    switch(event.getType()){
      case "payment_intent.succeeded" -> handlePaymentSucceeded(intent, event.getId());
//      case "payment_intent.payment_failed" -> (intent, event.getId());
//      case "payment_intent.succeeded" -> handlePaymentSucceeded(intent, event.getId());
//      case "payment_intent.succeeded" -> handlePaymentSucceeded(intent, event.getId());
      default ->
          log.info("Unhandled stripe event: {}", event.getType());
    }


    return ResponseEntity.ok("Received");

    }



    // ============= Handlers===================


    private void handlePaymentSucceeded(PaymentIntent intent, String eventId) {
      log.info("Handled payment intent: {}", intent.getId());

      paymentRepository.findByStripePaymentIntentId(intent.getId())
          .ifPresentOrElse((payment) -> {
            if(payment.getStatus() == PaymentStatus.SUCCESS){
              log.warn("Payment {} already done, skipping ", payment.getId());
              return;
            }

            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setPaidAt(LocalDateTime.now());
            payment.setStripeEventId(eventId);
            payment.setClientSecret(null);

            paymentRepository.save(payment);

            log.info("Payment updated to success for order id ={} ", payment.getOrderId());

            publishPaymentSucceeded(payment);

          }, ()-> log.error("Payment not found for intentid = {}", intent.getId()));
    }


    // ========== Kafka Publishers ==================

    private void publishPaymentSucceeded(Payment payment) {
      kafkaTemplate.send(
          KafkaTopicConfiguration.PAYMENT_SUCCESS,
          payment.getOrderId().toString(),
          PaymentSuccessEvent.builder()
              .orderId(payment.getOrderId())
              .customerId(payment.getCustomerId())
              .amount(payment.getAmount())
              .currency(payment.getCurrency())
              .stripePaymentIntentId(payment.getStripePaymentIntentId())
              .build()
      );

      log.info("Published payment-succeeded for order id ={} ",
          payment.getOrderId());
    }

    private void publishPaymentFailed(Payment payment, String reason) {
      kafkaTemplate.send(
          KafkaTopicConfiguration.PAYMENT_FAILED,
          payment.getOrderId().toString(),
          PaymentFailedEvent.builder()
              .orderId(payment.getOrderId())
              .customerId(payment.getCustomerId())
              .reason(reason)
              .build()
      );

      log.info("Published payment-failed for order id ={} ", payment.getOrderId());
    }

  }


