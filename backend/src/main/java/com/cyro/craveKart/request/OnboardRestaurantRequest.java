package com.cyro.cravekart.request;

import com.cyro.cravekart.models.ContactInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OnboardRestaurantRequest {

  private String name;
  private String description;
  private String cuisineType;
  private ContactInfo contactInfo;
  private String openingHours;
  private AddressRequest addressRequest;
  private List<String> images;
}



