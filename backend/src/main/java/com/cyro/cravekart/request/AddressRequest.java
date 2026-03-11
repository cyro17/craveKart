package com.cyro.cravekart.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AddressRequest {
  private String firstName;
  private String lastName;

  private String streetAddress;
  private String landmark;

  private String city;
  private String state;
  private String postalCode;
  private String country;

  private Boolean isDefault;

  private Double latitude;
  private Double longitude;

  private String deliveryInstruction;
}
