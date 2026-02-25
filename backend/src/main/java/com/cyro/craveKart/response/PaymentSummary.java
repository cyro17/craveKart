package com.cyro.cravekart.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentSummary {
  private Long paymentId;
  private String paymentMethod;   // CARD, UPI, COD
  private String paymentStatus;
}
