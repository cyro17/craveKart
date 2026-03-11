package com.cyro.cravekart.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AdminIngredientsRequest {

  private String categoryName;
  private List<String> ingredients = new ArrayList<>();
}
