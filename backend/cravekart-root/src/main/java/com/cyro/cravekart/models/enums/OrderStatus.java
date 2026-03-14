package com.cyro.cravekart.models.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum OrderStatus {


  PAYMENT_PENDING,    // order placed
  CONFIRMED,          // Stripe confirms this
  PREPARING,
  READY_FOR_PICKUP,
  OUT_FOR_DELIVERY,
  DELIVERED,

  PAYMENT_FAILED,       // payment failed updated by webhook
  CANCELLED

}
