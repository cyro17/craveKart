package com.cyro.cravekart.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RestaurantMenuResponse {

  private Long id;
  private String name;
  private List<FoodCategoryResponse> categories;

}
