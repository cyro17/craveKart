package com.cyro.cravekart.response;

import com.cyro.cravekart.models.Order;
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

  private LocalDateTime createdAt;
  private LocalDateTime estimatedDeliveryTime;

  public static OrderResponse from(Order order) {

    String paymentStatus;
    switch (order.getOrderStatus()){
      case PAYMENT_PENDING ->  paymentStatus = "PENDING";
      case PAID ->  paymentStatus = "PAID";
      case CANCELLED ->  paymentStatus = "FAILED";
      default ->  paymentStatus = "NOT_APPLICABLE";
    }

    PaymentSummary paymentSummary = PaymentSummary.builder()
        .paymentId(null)
        .paymentMethod(null)
        .paymentStatus(paymentStatus)
        .build();


    return OrderResponse.builder()
        .orderId(order.getId())
        .orderStatus(order.getOrderStatus())
        .restaurant(
            RestaurantSummary.builder()
                .restaurantId(order.getRestaurantId())
                .name(order.getRestaurantName())
                .restaurantAddress(order.getRestaurantAddress())
                .build()
        )
        .customer(
            CustomerSummary.builder()
                .customerId(order.getCustomerId())
                .name(order.getCustomerName())
                .build()
        )
        .items(
            order.getOrderItems().stream()
                .map(item-> OrderItemResponse.builder()
                    .foodName(item.getFoodName())
                    .quantity(item.getQuantity())
                    .unitPrice(item.getFoodPrice())
                    .totalPrice(item.getTotalPrice())
                    .imageUrl(item.getImageUrl())
                    .build())
                .toList()
        )

        .pricing(
            PriceBreakdown.builder()
                .subtotal(order.getSubtotal())
                .tax(order.getTaxAmount())
                .deliveryFee(order.getDeliveryFee())
                .restaurantCharge(order.getRestaurantCharge())
                .discount(order.getDiscount())
                .platformFee(order.getPlatformFee())
                .total(order.getTotalPrice())
                .build()
        )

        .delivery(order.getDeliveryAddressLine())
        .payment(paymentSummary)
        .createdAt(order.getCreatedAt())
        .estimatedDeliveryTime(
            order.getCreatedAt() != null ?
                order.getCreatedAt().plusMinutes(32) : null)

        .build();

  }
}
