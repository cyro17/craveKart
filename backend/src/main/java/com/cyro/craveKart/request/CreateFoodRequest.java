package com.cyro.cravekart.request;


import com.cyro.cravekart.models.Category;
import com.cyro.cravekart.models.IngredientItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFoodRequest {

  private String name;
  private String description;
  private Long price;

  private Category category;
  private List<String> images = new ArrayList<>();

  private Long restaurant_id;
  private boolean vegetarian;
  private boolean seasonal;

  private Set<IngredientItem> ingredientItems = new HashSet<>();
}
