package com.cyro.cravekart.controllers;


import com.cyro.cravekart.models.Category;
import com.cyro.cravekart.models.Food;
import com.cyro.cravekart.request.CreateFoodRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/food")
public class FoodController {

  @GetMapping("/health")

  @PostMapping
  public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest request) {

    return  null;
  }
}
