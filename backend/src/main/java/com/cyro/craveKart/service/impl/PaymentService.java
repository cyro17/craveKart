//package com.cyro.cravekart.service.impl;
//
//import com.cyro.cravekart.events.OrderCreatedEvent;
//import com.cyro.cravekart.events.PaymentFailedEvent;
//import com.cyro.cravekart.events.PaymentSuccessEvent;
//import com.cyro.cravekart.models.PaymentResponse;
//import com.cyro.cravekart.request.PaymentRequest;
//import com.stripe.Stripe;
//import com.stripe.exception.StripeException;
//import com.stripe.model.checkout.Session;
//import com.stripe.param.checkout.SessionCreateParams;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class PaymentService {
//  private final KafkaTemplate<String, Object> kafkaTemplate;
//
//  @Value("${stripe.publishableKey}")
//  private String publishableKey;
//
//  @Value("${stripe.secretKey}")
//  private String secretKey;
//
//  public PaymentResponse checkoutProducts(
//      PaymentRequest request
//  ){
//    Stripe.apiKey = secretKey;
//
//    SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
//        .setName(request.getName()).build();
//
//    SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
//        .setCurrency(request.getCurrency() == null ?
//            "USD" : request.getCurrency())
//        .setUnitAmount(request.getAmount())
//        .setProductData(productData)
//        .build();
//
//    SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
//        .setQuantity(request.getQuantity())
//        .setPriceData(priceData)
//        .build();
//
//    SessionCreateParams params = SessionCreateParams.builder()
//        .setMode(SessionCreateParams.Mode.PAYMENT)
//        .setSuccessUrl("http://localhost:3000/payment/success")
//        .setCancelUrl("http://localhost:3000/payment/cancel")
//        .addLineItem(lineItem)
//        .build();
//
//    Session session = null;
//    try{
//      session = Session.create(params);
//    } catch (StripeException e) {
//      throw new RuntimeException(e);
//    }
//
//    return PaymentResponse.builder()
//        .status("SUCCESS!!")
//        .message("Payment Succesful")
//        .sessionId(session.getId())
//        .sessionUrl(session.getUrl())
//        .build();
//
//  }
//
//
//  @KafkaListener(
//      topics = "order-created",
//      groupId = "payment-group",
//      containerFactory = "kafkaListenerContainerFactory"
//  )
//  public void processPayment(
//      OrderCreatedEvent event
//  ){
//    log.info("Processing payment for orderId={}", event.getOrderId());
//    Stripe.apiKey = secretKey;
//    try {
//
//      SessionCreateParams.LineItem.PriceData.ProductData productData =
//          SessionCreateParams.LineItem.PriceData.ProductData.builder()
//              .setName("Order #" + event.getOrderId())
//              .build();
//
//      SessionCreateParams.LineItem.PriceData priceData =
//          SessionCreateParams.LineItem.PriceData.builder()
//              .setCurrency(event.getCurrency())
//              .setUnitAmount(event.getAmount())
//              .setProductData(productData)
//              .build();
//
//      SessionCreateParams.LineItem lineItem =
//          SessionCreateParams.LineItem.builder()
//              .setQuantity(1L)
//              .setPriceData(priceData)
//              .build();
//
//      SessionCreateParams params = SessionCreateParams.builder()
//          .setMode(SessionCreateParams.Mode.PAYMENT)
//          .setSuccessUrl("http://localhost:3000/payment/success")
//          .setCancelUrl("http://localhost:3000/payment/cancel")
//          .addLineItem(lineItem)
//          .build();
//
//      Session session = Session.create(params);
//
//      kafkaTemplate.send("payment-success",
//          event.getOrderId().toString(),
//          new PaymentSuccessEvent(
//              event.getOrderId(), session.getId()
//          ));
//
//      log.info("Payment sent successfully");
//
//    } catch (Exception e) {
//        kafkaTemplate.send("payment-failed",
//            event.getOrderId().toString(),
//            new PaymentFailedEvent(event.getOrderId())
//        );
//
//        log.error("Payment failed for orderId={}", event.getOrderId());
//    }
//  }
//}
//
