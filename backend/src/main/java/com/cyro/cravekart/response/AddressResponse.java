package com.cyro.cravekart.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponse {

  private Long id;
  private String name;
  private String firstName;
  private String lastName;
  private String contactNumber;
  private String streetAddress;
  private String landmark;
  private String city;
  private  String state;
  private String zipCode;
  private String country;
  private String fullAddress;
  private Boolean isDefault;
  private BigDecimal latitude;
  private BigDecimal longitude;
  private String deliveryInstruction;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

}
