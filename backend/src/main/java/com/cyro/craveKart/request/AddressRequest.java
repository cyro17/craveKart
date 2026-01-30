package com.cyro.cravekart.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AddressRequest {
  private String streetAddress;
  private String city;
  private String state;
  private String postalCode;
  private String country;
}
