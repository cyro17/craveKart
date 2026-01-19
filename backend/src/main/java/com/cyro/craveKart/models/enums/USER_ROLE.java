package com.cyro.cravekart.models.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum USER_ROLE {
  CUSTOMER,
  RESTAURANT_PARTNER,
  DELIVERY_PARTNER,
  RESTAURANT_OWNER,
  ADMIN
}
