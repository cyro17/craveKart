package com.cyro.cravekart.models.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum PaymentType {
  CARD,
  COD,
  UPI
}
