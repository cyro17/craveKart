package com.cyro.cravekart.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInitiatedEvent {
  private Long orderId;
  private Long customerId;
  private String clientSecret;
}
