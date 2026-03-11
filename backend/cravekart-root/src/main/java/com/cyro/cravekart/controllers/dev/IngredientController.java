package com.cyro.cravekart.controllers.dev;

import com.cyro.cravekart.models.IngredientCategory;
import com.cyro.cravekart.models.IngredientItem;
import com.cyro.cravekart.request.CreateIngredientCategoryRequest;
import com.cyro.cravekart.request.CreateIngredientItemRequest;
import com.cyro.cravekart.service.IngredientsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
@RequiredArgsConstructor
public class IngredientController {

  private final IngredientsService ingredientsService;

  @GetMapping("/check")
  public ResponseEntity<String> getHealth() {
    return ResponseEntity.ok("INGREDIENT CONTROLLER OK");
  }

  @PostMapping("/category")
  public ResponseEntity<IngredientCategory> createIngCategory(
      @Valid @RequestBody CreateIngredientCategoryRequest createIngredientCategoryRequest
  ){
    IngredientCategory ingredientCategory =
        ingredientsService.createIngredientCategory(createIngredientCategoryRequest);
    return new ResponseEntity<>(ingredientCategory, HttpStatus.CREATED);
  }

  @GetMapping("/restaurant/{restaurantId}/category")
  public ResponseEntity<List<IngredientCategory>> getByRestaurant(
      @PathVariable Long restaurantId) throws Exception {

    List<IngredientCategory> categories = ingredientsService.findByRestaurantId(restaurantId);
    return new ResponseEntity<>(categories, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<IngredientItem> createIngItem(
      @RequestBody CreateIngredientItemRequest request){
    IngredientItem ingredientItem = ingredientsService.createIngredientItem(request);
    return new ResponseEntity<>(ingredientItem, HttpStatus.CREATED);
  }

  @PutMapping("/{id}/stock")
  public ResponseEntity<IngredientItem> updateStock(@PathVariable Long ingredientId){
    IngredientItem ingredientItem = ingredientsService.updateStock(ingredientId);
    return  new ResponseEntity<>(ingredientItem, HttpStatus.ACCEPTED);
  }







}
