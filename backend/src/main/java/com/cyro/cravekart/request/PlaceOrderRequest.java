package com.cyro.cravekart.request;

import com.cyro.cravekart.models.enums.DeliveryType;
import com.cyro.cravekart.models.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderRequest {

  private Long addressId;
  private DeliveryType deliveryType;
  private PaymentType paymentMethod;
  private String voucherCode;
  private String specialInstruction;
}



