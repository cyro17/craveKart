package com.cyro.cravekart.service.impl;

import com.cyro.cravekart.response.PaymentIntentResult;
import com.cyro.cravekart.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StripeServiceImpl implements StripeService {



  @Override
  public PaymentIntentResult createPaymentIntent(
      Long amount, String currency, Long orderId) throws StripeException {

    PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
        .setAmount(amount)
        .setCurrency(currency)
        .putMetadata("orderId", orderId.toString())
        .build();


    PaymentIntent paymentIntent = PaymentIntent.create(params);
    log.info("Payment intent created: {} for orderId ", paymentIntent.getId(), orderId);

    return PaymentIntentResult.builder()
        .paymentIntentId(paymentIntent.getId())
        .clientSecret(paymentIntent.getClientSecret()).build();
  }
}
