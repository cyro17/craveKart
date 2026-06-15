package com.cyro.cravekart.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {
  private Long cartId;
  private String restaurantName;
  private List<CartItemResponse> items;
  private PriceBreakdown pricing;
}
