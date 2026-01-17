package com.cyro.cravekart.response;


import com.cyro.cravekart.models.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceOrder {

  private Long orderId;
  private OrderStatus orderStatus;
  private BigDecimal totalPrice;

  @CreationTimestamp
  private LocalDateTime createdAt;
}
