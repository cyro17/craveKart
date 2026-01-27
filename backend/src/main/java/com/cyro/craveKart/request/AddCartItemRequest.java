package com.cyro.cravekart.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddCartItemRequest {

  @NotNull
  private Long foodId;
  @NotNull
  @Min(1)
  private Integer quantity;
}
