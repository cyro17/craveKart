package com.cyro.cravekart.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCreatedEvent {

  private Long orderId;
  private Long customerId;
  private Long restaurantId;
  private String customerName;
  private String currency;
  private Long amount;

}
