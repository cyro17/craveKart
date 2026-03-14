package com.cyro.cravekart.controllers;

import com.cyro.cravekart.config.kafka.KafkaTopicConfiguration;
import com.cyro.cravekart.events.payment.PaymentFailedEvent;
import com.cyro.cravekart.events.payment.PaymentSuccessEvent;
import com.cyro.cravekart.models.Payment;
import com.cyro.cravekart.repository.PaymentRepository;
import com.stripe.exception.EventDataObjectDeserializationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
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
@RequestMapping("/api/webhook/")
@RequiredArgsConstructor
@Slf4j
public class StripeWebhookHandler {


  @Value("${stripe.api.webhook-secret}")
  private String webhookSecret;

  private final KafkaTemplate<String, Object> kafkaTemplate;
  private final PaymentRepository paymentRepository;

  @PostMapping("stripe")
  public ResponseEntity<String> handleWebhook(
      @RequestBody String payload,
      @RequestHeader("Stripe-Signature") String sigHeader
  ){

    // verify payment event
    
    Event event;
    try {
      event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
      log.info("✅ Signature OK. Event type={}, id={}", event.getType(), event.getId());

    } catch (Exception e) {
      log.error("Invalid webhook signature: {}", e.getMessage());
      return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
    }

    PaymentIntent paymentIntent = getPaymentIntent(event);

    if(event.getId() != null && paymentRepository.existsByStripeEventId(event.getId())) {
      log.warn("Duplicate payment id for event {}", event.getId());
      return  ResponseEntity.status(HttpStatus.OK).body("Already processed");
    }

    switch(event.getType()){
      case "payment_intent.succeeded" -> handlePaymentSucceeded(paymentIntent, event.getId());

      default ->
          log.info("Unhandled stripe event: {}", event.getType());
    }


    return ResponseEntity.ok("Received");

    }

  private static PaymentIntent getPaymentIntent(Event event) {
    EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();

    PaymentIntent paymentIntent = null;

    if (deserializer.getObject().isPresent()) {
      paymentIntent = (PaymentIntent) deserializer.getObject().get();
    } else {
      // Fallback to unsafe deserialization
      try {
        paymentIntent = (PaymentIntent) deserializer.deserializeUnsafe();

      } catch (EventDataObjectDeserializationException e) {
        throw new RuntimeException("Unable to Deserialize Payment Event to Payment Intent Object");
      }
    }
    return paymentIntent;
  }


  // ============= Handlers===================


    private void handlePaymentSucceeded(PaymentIntent intent, String eventId) {
      log.info("Handled payment intent: {}", intent.getId());
      log.info("Payment intent received for event {}", eventId, intent);

      paymentRepository.findByStripePaymentIntentId(intent.getId())
          .ifPresentOrElse(
                  payment -> {

            payment.setPaidAt(LocalDateTime.now());
            payment.setStripeEventId(eventId);
            payment.setClientSecret(null);

            paymentRepository.save(payment);

            log.info("Payment updated to success for order id ={} ", payment.getOrderId());

            publishPaymentSucceeded(payment);

          }, ()-> log.error("Payment not found for intent id  = {}", intent.getId()));
    }

    private void handlePaymentFailed(PaymentIntent intent, String eventId) {
        paymentRepository.findByStripePaymentIntentId(intent.getId())
                .ifPresentOrElse(
                        payment-> {
                          payment.setFailedAt(LocalDateTime.now());
                          payment.setStripeEventId(eventId);
                          payment.setFailureReason(
                                  intent.getLastPaymentError() != null ?
                                          intent.getLastPaymentError().getMessage() :
                                          "Payment declined"
                          );
                          payment.setFailureCode(
                                  intent.getLastPaymentError() != null ?
                                          intent.getLastPaymentError().getCode()
                                          : null
                          );

                          paymentRepository.save(payment);

                          log.info("Published payment-failed for order id = {}", payment.getOrderId());

                          PaymentFailedEvent failedEvent = PaymentFailedEvent.builder().orderId(payment.getOrderId())
                                  .orderId(payment.getOrderId())
                                  .customerId(payment.getCustomerId())
                                  .reason(payment.getFailureReason())
                                  .build();
                          publishPaymentFailed(payment, failedEvent);

                        }, ()-> log.error("Payment not found for intent id  = {}", intent.getId())
                );

    }


    // ========== Kafka Publishers ==================

    private void publishPaymentSucceeded(Payment payment) {
      PaymentSuccessEvent event = PaymentSuccessEvent.builder()
              .orderId(payment.getOrderId())
              .customerId(payment.getCustomerId())
              .amount(payment.getAmount())
              .currency(payment.getCurrency())
              .stripePaymentIntentId(payment.getStripePaymentIntentId())
              .build();

      kafkaTemplate.send(
          KafkaTopicConfiguration.PAYMENT_SUCCESS,
          payment.getOrderId().toString(),
          event
      );

      log.info("Published payment-succeeded for order id ={} ",
          payment.getOrderId());

    }

    private void publishPaymentFailed(Payment payment, PaymentFailedEvent event) {

    kafkaTemplate.send(
          KafkaTopicConfiguration.PAYMENT_FAILED,
          payment.getOrderId().toString(),
          event
      );

      log.info("Published payment-failed for order id ={} ", payment.getOrderId());
    }

  }


