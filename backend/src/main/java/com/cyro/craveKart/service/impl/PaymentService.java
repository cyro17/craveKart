package com.cyro.cravekart.service.impl;

import com.cyro.cravekart.models.PaymentResponse;
import com.cyro.cravekart.request.PaymentRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

  @Value("${stripe.publishableKey}")
  private String publishableKey;

  @Value("${stripe.secretKey}")
  private String secretKey;

  public PaymentResponse checkoutProducts(
      PaymentRequest request
  ){
    Stripe.apiKey = secretKey;

    SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
        .setName(request.getName()).build();

    SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
        .setCurrency(request.getCurrency() == null ?
            "USD" : request.getCurrency())
        .setUnitAmount(request.getAmount())
        .setProductData(productData)
        .build();

    SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
        .setQuantity(request.getQuantity())
        .setPriceData(priceData)
        .build();

    SessionCreateParams params = SessionCreateParams.builder()
        .setMode(SessionCreateParams.Mode.PAYMENT)
        .setSuccessUrl("https://localhost:8091/success")
        .setCancelUrl("https://localhost:8091/cancel")
        .addLineItem(lineItem)
        .build();

    Session session = null;
    try{
      session = Session.create(params);
    } catch (StripeException e) {
      throw new RuntimeException(e);
    }

    return PaymentResponse.builder()
        .status("SUCCESS!!")
        .message("Payment Succesful")
        .sessionId(session.getId())
        .sessionUrl(session.getUrl())
        .build();

  }

}

