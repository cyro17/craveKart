package com.cyro.cravekart.request;

import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class CreateFoodRequest {

  private String name;
  private String description;
  private BigDecimal price;

  private Long restaurant_id;
  private Long FoodCategoryId;

  private Boolean seasonal;
  private Boolean vegetarian;
  private Boolean available;

  private Set<Long> ingredientItemsIds = new HashSet<>();
  private List<String> images;

}
