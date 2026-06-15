package com.cyro.cravekart.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AddressRequest {
  private String firstName;
  private String lastName;

  @NotBlank(message = "Street address is required")
  private String streetAddress;

  private String landmark;

  @NotBlank(message = "city is required")
  private String city;

  @NotBlank(message = "state is required")
  private String state;

  @NotBlank(message = "Postal code is required")
  @Pattern(regexp = "^[0-9]{6}$", message = "Postal code must be 6 digits")
  private String postalCode;

  @NotBlank(message = "Country is required")
  private String country;

  private Boolean isDefault = true;
  private Double latitude;
  private Double longitude;
  private String deliveryInstruction;
}
