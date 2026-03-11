package com.cyro.cravekart.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemResponse {

//  private Long foodId;
  private String foodName;
  private Integer quantity;
  private BigDecimal unitPrice;
  private BigDecimal totalPrice;
  private String imageUrl;
}
