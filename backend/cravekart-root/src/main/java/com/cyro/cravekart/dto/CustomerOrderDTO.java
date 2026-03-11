package com.cyro.cravekart.dto;

import com.cyro.cravekart.models.Payment;
import com.cyro.cravekart.models.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrderDTO {

  private Long orderId;
  private OrderStatus customerOrderStatus;

  private CartDto cart;
  private Double totalAmount;
  private Double deliveryCharges;
  private Double platformFee;
  private Double grandTotal;

  private RestaurantDto restaurantDto;
  private CustomerDto customerDto;
  private Payment PaymentMethod;
  private Integer estimatedPreparationTime;

  private String otp;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime orderAcceptedTime;

}
