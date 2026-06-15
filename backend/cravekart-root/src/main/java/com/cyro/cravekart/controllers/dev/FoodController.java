package com.cyro.cravekart.controllers.dev;

import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.exception.FoodException;
import com.cyro.cravekart.models.Food;
import com.cyro.cravekart.models.FoodCategory;
import com.cyro.cravekart.models.RestaurantPartner;
import com.cyro.cravekart.request.AddFoodRequest;
import com.cyro.cravekart.request.CreateCategoryRequest;
import com.cyro.cravekart.response.CreateCategoryResponse;
import com.cyro.cravekart.response.FoodResponse;
import com.cyro.cravekart.service.FoodCategoryService;
import com.cyro.cravekart.service.FoodService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurantPartner/food")
@RequiredArgsConstructor
public class FoodController {
  private final FoodService foodService;
  private final AuthContextService authContextService;
  private final FoodCategoryService foodCategoryService;
  private final ModelMapper modelMapper;

  @GetMapping("/health")
  public ResponseEntity<String> check() {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<FoodResponse> addFood(@Valid @RequestBody AddFoodRequest request)
      throws FoodException {
    RestaurantPartner restaurantPartner = authContextService.getRestaurantPartner();
    Long restaurantId = restaurantPartner.getRestaurant().getId();
    request.setRestaurant_id(restaurantId);

    Food food = foodService.addFood(request);
    return new ResponseEntity<>(modelMapper.map(food, FoodResponse.class), HttpStatus.CREATED);
  }

  @DeleteMapping("/{foodId}")
  public ResponseEntity<String> deleteFood(@PathVariable Long foodId) {
    RestaurantPartner restaurantPartner = authContextService.getRestaurantPartner();
    Long restaurantID = restaurantPartner.getRestaurant().getId();

    boolean deleted = foodService.deleteFood(foodId, restaurantID);
    if (!deleted) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    return new ResponseEntity<>("Food item deleted", HttpStatus.OK);
  }

  @PatchMapping("/{foodId}/availability")
  public ResponseEntity<FoodResponse> toggleAvailability(@PathVariable Long foodId) {
    Long restaurantId = authContextService.getRestaurantPartner().getRestaurant().getId();
    FoodResponse response = foodService.updateAvailibilityStatus(foodId, restaurantId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  //  ---- CATEGORY ------------------------------------
  @PostMapping("/category")
  public ResponseEntity<CreateCategoryResponse> addCategory(CreateCategoryRequest request)
      throws FoodException {
    Long restaurantId = authContextService.getRestaurantPartner().getRestaurant().getId();
    request.setRestaurant_id(restaurantId);

    CreateCategoryResponse category = foodCategoryService.createCategory(request);
    return new ResponseEntity<>(category, HttpStatus.CREATED);
  }

  @GetMapping("/category")
  public ResponseEntity<?> getCategories() {
    Long restaurantId = authContextService.getRestaurantPartner().getRestaurant().getId();
    List<FoodCategory> categoryByRestaurantId =
        foodCategoryService.findCategoryByRestaurantId(restaurantId);

    return ResponseEntity.ok(categoryByRestaurantId);
  }
}
