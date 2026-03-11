package com.cyro.cravekart.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateIngredientItemRequest {

  @NotBlank
  private String name;
  @NotNull
  private Long categoryId;
  @NotNull
  private Long restaurantId;

  private Boolean inStock = true;
}
