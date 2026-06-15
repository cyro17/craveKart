package com.cyro.cravekart.request;

import com.cyro.cravekart.models.enums.DeliveryType;
import com.cyro.cravekart.models.enums.PaymentType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderRequest {

  private Long addressId;

  @NotNull(message = "Delivery type is required")
  private DeliveryType deliveryType;

  private PaymentType paymentMethod;
  private String voucherCode;
  private String specialInstruction;
}
