package com.cyro.cravekart.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FoodCategoryResponse {
  private Long id;
  private String name;
  private List<FoodResponse> foods;

}
