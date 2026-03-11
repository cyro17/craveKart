package com.cyro.cravekart.controllers.dev;

import com.cyro.cravekart.config.security.AuthService;
import com.cyro.cravekart.exception.RestaurantException;
import com.cyro.cravekart.exception.UserException;
import com.cyro.cravekart.models.FoodCategory;
import com.cyro.cravekart.request.CreateCategoryRequest;
import com.cyro.cravekart.response.CreateCategoryResponse;
import com.cyro.cravekart.service.FoodCategoryService;
import com.cyro.cravekart.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FoodCategoryController {

  private final FoodCategoryService foodCategoryService;
  private final UserService userService;
  private final AuthService authService;

  @GetMapping("/admin/category/check")
  public ResponseEntity<String> check() {
    return   ResponseEntity.ok("OK");
  }

  @PostMapping("/admin/category")
  public ResponseEntity<CreateCategoryResponse> createCategory(
      @RequestBody CreateCategoryRequest createCategoryRequest) throws RestaurantException {
    CreateCategoryResponse response =
        foodCategoryService.createCategory(createCategoryRequest);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping("/admin/category")
  public ResponseEntity<List<FoodCategory>> getAllCategories() {
    List<FoodCategory> allFoodCategories = foodCategoryService.getAllFoodCategories();
    return  new ResponseEntity<>(allFoodCategories, HttpStatus.OK);
  }

  @GetMapping("/category/restaurant/{id}")
  public ResponseEntity<List<FoodCategory>> getRestaurantsCategory(
      @PathVariable Long id) throws RestaurantException, UserException {

    List<FoodCategory> categories=foodCategoryService.findCategoryByRestaurantId(id);
    return new ResponseEntity<>(categories,HttpStatus.OK);
  }

}