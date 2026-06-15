package com.cyro.cravekart.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Data;

@Data
public class AddFoodRequest {

  @NotBlank(message = "Food name is required")
  private String name;

  private String description;

  @NotNull(message = "Price is required")
  @DecimalMin(value = "0.1", message = "Price must be greater than 0")
  private BigDecimal price;

  private Long restaurant_id; // set by controller from JWT — not required in request

  @NotBlank(message = "Food category name is required")
  private String foodCategoryName;

  private Boolean seasonal = false;
  private Boolean vegetarian = false;
  private Boolean available = true;

  private Set<Long> ingredientItemsIds = new HashSet<>();
  private List<String> images;
}
