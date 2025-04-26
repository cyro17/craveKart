package com.cyro.craveKart.service;

import com.cyro.craveKart.exception.RestaurantException;
import com.cyro.craveKart.model.IngredientCategory;
import com.cyro.craveKart.model.IngredientsItem;
import org.bson.types.ObjectId;

import java.util.List;

public interface IngredientsService {
    public IngredientCategory createIngredientsCategory(String name, ObjectId restaurantId)
            throws RestaurantException;

    public IngredientCategory findIngredientsCategoryById(ObjectId id) throws Exception;

    public List<IngredientCategory> findIngredientsCategoryByRestaurantId(ObjectId id) throws Exception;

    public List<IngredientsItem> findRestaurantsIngredients(
            Long restaurantId);


    public IngredientsItem createIngredientsItem(ObjectId restaurantId,
                                                 String ingredientName, ObjectId ingredientCategoryId) throws Exception;

    public IngredientsItem updateStock(ObjectId id) throws Exception;

}
