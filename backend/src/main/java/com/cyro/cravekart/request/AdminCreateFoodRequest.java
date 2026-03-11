package com.cyro.cravekart.request;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class AdminCreateFoodRequest {

  @NotBlank
  private String name;
  private String description;

  private BigDecimal price;

  @NotBlank
  private String foodCategoryName;

  private List<String> images = new ArrayList<>();

  @NotNull
  private Long restaurantId;

  private Boolean vegetarian = false;
  private Boolean seasonal = false;
  private Boolean available = true;

  private List<AdminIngredientsRequest> ingredientsRequests;



}
