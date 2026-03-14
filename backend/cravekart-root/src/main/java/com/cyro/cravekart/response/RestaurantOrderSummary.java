package com.cyro.cravekart.response;

import com.cyro.cravekart.models.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class RestaurantOrderSummary {

    private Long orderId;
    private OrderStatus orderStatus;

    private String customerName;
    private String customerPhone;
    private String deliveryAddressLine;
    private String specialInstruction;

    private List<OrderItemResponse> items;
    private Integer totalItems;

    private BigDecimal subtotal;
    private BigDecimal restaurantCharge;
    private BigDecimal totalPrice;

    private LocalDateTime createdAt;
    private LocalDateTime acceptedAt;
    private LocalDateTime preparedAt;


}
