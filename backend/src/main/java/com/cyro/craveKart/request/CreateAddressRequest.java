package com.cyro.cravekart.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAddressRequest {

  @NotBlank(message = "First name is required")
  private String firstName;

  @NotBlank(message = "Last name is required")
  private String lastName;

  @NotBlank(message = "Street address is required")
  private String streetAddress;

  @NotBlank(message = "City is required")
  private String landmark;

  @NotBlank(message = "City is required")
  private String city;

  private String state;

  @NotBlank(message = "Postal code is required")
  private String postalCode;

  @NotBlank(message = "Country is required")
  private String country;

  private Double latitude;
  private Double longitude;

  @Size(max = 500)
  private String deliveryInstruction;

}
