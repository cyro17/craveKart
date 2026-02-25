package com.cyro.cravekart.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderNotifcationEvent {
  private Long orderId;
  private Long customerName;
  private String customerEmail;
  private String restaurantName;
  private BigDecimal totalPrice;
}
