package com.cyro.cravekart.response;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class OrderResponse {

  private Long orderId;
  private String orderStatus;
  private BigDecimal totalAmount;
  private int totalItems;

  @CreationTimestamp
  private LocalDateTime createdAt;


}
