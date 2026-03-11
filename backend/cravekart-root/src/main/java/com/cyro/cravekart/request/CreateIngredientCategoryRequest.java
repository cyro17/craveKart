package com.cyro.cravekart.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateIngredientCategoryRequest {

  @NotBlank
  private String name;

  @NotNull
  private Long restaurantId;

}
