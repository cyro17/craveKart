package com.cyro.cravekart.response;

import com.cyro.cravekart.models.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrderResponse {
  private Long id;
  private String restaurantName;
  private BigDecimal totalPrice;
  private int totalItems;
  private List<CustomerOrderItemResponse> orterItems = new ArrayList<>() ;
  private OrderStatus orderStatus;

  private LocalDateTime createdAt;
}
