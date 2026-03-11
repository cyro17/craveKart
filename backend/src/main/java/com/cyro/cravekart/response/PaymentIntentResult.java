package com.cyro.cravekart.response;

import com.stripe.model.PaymentIntent;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentIntentResult {
  private String paymentIntentId;
  private String clientSecret;
}
