package com.cyro.cravekart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodItemDto {

  private Long id;
  private String name;
  private String description;
  private FoodDto food;

  @BooleanFlag
  @JsonProperty("isAvailable")
  private Boolean isAvailable;

  private String FoodItemCategory;
  private BigDecimal rating;
  @NotNull(message = "price should be provided")
  private BigDecimal price;
}
