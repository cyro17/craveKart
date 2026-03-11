package com.cyro.cravekart.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestaurantSummary {

  private Long restaurantId;
  private String name;
  private String restaurantAddress;
//  private String imageUrl;
}
