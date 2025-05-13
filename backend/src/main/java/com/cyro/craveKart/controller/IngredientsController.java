package com.cyro.craveKart.controller;

import com.cyro.craveKart.exception.RestaurantException;
import com.cyro.craveKart.model.IngredientCategory;
import com.cyro.craveKart.model.IngredientsItem;
import com.cyro.craveKart.request.CreateIngredientRequest;
import com.cyro.craveKart.service.IngredientsServiceImpl;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientsController {

    @Autowired
    private IngredientsServiceImpl ingredientsService;

    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(
            @RequestBody CreateIngredientRequest createIngredientRequest)
            throws RestaurantException {
        IngredientCategory ingredientsCategory = ingredientsService.createIngredientsCategory(createIngredientRequest.getName(),
                createIngredientRequest.getRestaurantId());
        return  new ResponseEntity<>(ingredientsCategory, HttpStatus.CREATED);
    }

    @PostMapping()
    public ResponseEntity<IngredientsItem> createIngredient(@RequestBody CreateIngredientRequest request) throws Exception {
        IngredientsItem ingredientsItem = ingredientsService.createIngredientsItem(request.getRestaurantId(), request.getName(),
                request.getIngredientCategoryId());
        return  new ResponseEntity<>(ingredientsItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<IngredientsItem> updateStock(@PathVariable ObjectId id) throws Exception{
        IngredientsItem ingredientsItem = ingredientsService.updateStock(id);
        return  new ResponseEntity<>(ingredientsItem, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<IngredientsItem>> restaurantsIngredient(
            @PathVariable Long id) throws Exception{
        List<IngredientsItem> restaurantsIngredients = ingredientsService.findRestaurantsIngredients(id);
        return  new ResponseEntity<>(restaurantsIngredients, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientCategory>> restaurantsIngredientCategory(
            @PathVariable ObjectId id) throws Exception{
        List<IngredientCategory> items=ingredientsService.findIngredientsCategoryByRestaurantId(id);
        return new ResponseEntity<>(items,HttpStatus.OK);
    }

}
