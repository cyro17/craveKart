package com.cyro.cravekart.models.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum OrderStatus {

  CREATED,              // order placed
  PAYMENT_PENDING,      // waiting for Stripe confirmation
  PAID,                 // payment success updated by webhook
  PAYMENT_FAILED,       // payment failed updated by webhook

  CONFIRMED,
  PREPARING,
  READY_FOR_PICKUP,
  OUT_FOR_DELIVERY,
  DELIVERED,

  CANCELLED

}
