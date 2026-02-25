package com.cyro.cravekart.response;

import com.cyro.cravekart.models.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {

  private Long orderId;
  private OrderStatus orderStatus;

  private RestaurantSummary restaurant;
  private CustomerSummary customer;

  private List<OrderItemResponse> items;
  private PriceBreakdown pricing;
  private String delivery;
  private PaymentSummary payment;

  @CreationTimestamp
  private LocalDateTime createdAt;
  private LocalDateTime estimatedDeliveryTime;
}
