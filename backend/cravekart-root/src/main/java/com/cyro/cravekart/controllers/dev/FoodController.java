package com.cyro.cravekart.controllers.dev;


import com.cyro.cravekart.exception.FoodException;
import com.cyro.cravekart.models.Food;
import com.cyro.cravekart.request.CreateFoodRequest;
import com.cyro.cravekart.service.FoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
@RequiredArgsConstructor
public class FoodController {
  private final FoodService foodService;
  @GetMapping("/health")

  @PostMapping
  public ResponseEntity<Food> createFood(
      @Valid @RequestBody CreateFoodRequest request) throws FoodException {
    Food food = foodService.createFood(request);
    return  new ResponseEntity<>(food, HttpStatus.CREATED);
  }
}
