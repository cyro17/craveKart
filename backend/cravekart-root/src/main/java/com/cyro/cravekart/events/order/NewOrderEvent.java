package com.cyro.cravekart.events.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewOrderEvent {
  private Long orderId;
  private Long customerId;
  private Long restaurantId;
  private String customerName;
  private String customerPhone;
  private String restaurantName;
  private String specialInstructions;
  private BigDecimal subtotal;
  private BigDecimal totalPrice;
  private String deliveryAddress;

  private Integer totalItems;
  private List<Item> items;

  private LocalDateTime createdAt;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Item {
    private String foodName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private String imageUrl;
  }
}
