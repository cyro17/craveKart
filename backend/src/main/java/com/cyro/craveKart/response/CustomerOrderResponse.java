package com.cyro.cravekart.response;

import com.cyro.cravekart.models.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class CustomerResponse {
  private Long orderId;
  private String restaurantName;
  private BigDecimal totalPrice;
  private int totalItems;
  private OrderStatus orderStatus;

  private LocalDateTime createdAt;
}
