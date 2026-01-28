package com.cyro.cravekart.response;


import com.cyro.cravekart.models.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcceptOrderResponse {

  private Long id;
  private OrderStatus status;
  @JsonIgnore
  private CartResponse cart;

  private BigDecimal totalAmount;
  private BigDecimal deliveryCharges;
  private BigDecimal platformFee;
  private BigDecimal grandTotal;

}
