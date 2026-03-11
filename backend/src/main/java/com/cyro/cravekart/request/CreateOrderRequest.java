package com.cyro.cravekart.request;

import com.cyro.cravekart.models.IngredientItem;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CreateOrderRequest {

  private String name;
  private String description;
  private BigDecimal price;

  private Long categoryId;
  private List<String> images;

  private Long restaurantId;

  private Boolean vegetarian;
  private Boolean seasonal;

  private List<IngredientItem> ingredientItems;
}
