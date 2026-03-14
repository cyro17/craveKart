package com.cravekart.core.events.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderConfirmedEvent {

    private Long orderId;
    private Long customerId;
    private String customerName;
    private String customerEmail;
    private String restaurantName;

    private String totalPrice;
    private String estimatedDeliveryTime;
}
