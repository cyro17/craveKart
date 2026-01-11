package com.cyro.cravekart.request;

import com.cyro.cravekart.models.Address;
import com.cyro.cravekart.models.ContactInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRestaurantRequest {

  private Long id;
  private String name;
  private String description;
  private String cuisineType;
  private Address address;
  private ContactInfo contactInformation;
  private String openingHours;
  private List<String> images;
  private LocalDateTime registrationDate;
}
