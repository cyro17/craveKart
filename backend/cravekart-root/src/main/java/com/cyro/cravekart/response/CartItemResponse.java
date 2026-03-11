package com.cyro.cravekart.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemResponse {
  private Long cartItemId;
  private Long foodId;
  private String foodName;
  private String description;
  private Integer quantity;
  private BigDecimal totalPrice;
  private String restaurantName;
  private List<String> images;
}
