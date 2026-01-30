package com.cyro.cravekart.request;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum USER_STATUS {

  ACTIVE,
  EXPIRED,
  INACTIVE,
  SUSPENDED,
}
