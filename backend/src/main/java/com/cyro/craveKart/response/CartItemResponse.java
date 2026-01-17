package com.cyro.cravekart.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
  private Long cartItemId;
  private Long foodId;
  private String foodName;
  private Integer quantity;
  private BigDecimal totalPrice;
}
