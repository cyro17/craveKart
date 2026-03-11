package com.cyro.cravekart.service;

import com.cyro.cravekart.response.PaymentIntentResult;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;


public interface StripeService {

  public PaymentIntentResult createPaymentIntent(Long amount,
                                                 String currency,
                                                 Long orderId) throws StripeException;
}
