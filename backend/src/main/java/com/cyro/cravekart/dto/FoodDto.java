package com.cyro.cravekart.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jdk.jfr.BooleanFlag;

import java.util.List;

public class FoodDto {

  private Long id;

  @NotBlank(message = "Name of the menu should be provided")
  private String foodName;

  private List<FoodItemDto> foodItems;

  @BooleanFlag
  @JsonProperty("isActive")
  private Boolean isActive;

  @JsonIgnore
  private RestaurantDto restaurant;
}
