package com.cyro.cravekart.request;


import com.cyro.cravekart.models.Restaurant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCategoryRequest {

  @NotBlank(message = "Category name is required")
  private String name;

  @NotNull(message = "Restaurant id is required")
  private Long restaurant_id;
}
