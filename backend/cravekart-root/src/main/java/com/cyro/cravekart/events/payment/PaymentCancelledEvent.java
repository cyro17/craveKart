package com.cyro.cravekart.events.payment;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PaymentCancelledEvent {
  private Long orderId;
  private Long customerId;
  private String reason;
}
