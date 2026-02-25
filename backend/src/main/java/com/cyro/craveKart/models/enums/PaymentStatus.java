package com.cyro.cravekart.models.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum PaymentStatus {
  PENDING,
  REQUIRES_ACTION,
  SUCCESS,
  FAILED,
  CANCELLED,
  REFUNDED
}
