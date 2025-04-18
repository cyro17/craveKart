package com.cyro.craveKart.service;

import com.cyro.craveKart.exception.RestaurantException;
import com.cyro.craveKart.model.IngredientCategory;
import com.cyro.craveKart.model.IngredientsItem;
import com.cyro.craveKart.model.Restaurant;
import com.cyro.craveKart.repository.IngredientsCategoryRepository;
import com.cyro.craveKart.repository.IngredientsItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientsServiceImpl implements IngredientsService {

    @Autowired
    private IngredientsCategoryRepository ingredientsCategoryRepo;

    @Autowired
    private IngredientsItemRepository ingredientsItemRepository;

    @Autowired
    private RestaurantServiceImpl restaurantService;

    @Override
    public IngredientCategory createIngredientsCategory(String name, Long restaurantId)
            throws RestaurantException {
        IngredientCategory ingredientCategory_ = ingredientsCategoryRepo.findByRestaurantIdAndNameIgnoreCase(restaurantId, name);
        if(ingredientCategory_ != null ) return  ingredientCategory_;

        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

        IngredientCategory ingredientCategory = new IngredientCategory();
        ingredientCategory.setRestaurant(restaurant);
        ingredientCategory.setName(name);

        IngredientCategory createdCategory = ingredientsCategoryRepo.save(ingredientCategory);
        return  createdCategory;
    }

    @Override
    public IngredientCategory findIngredientsCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> ing_op = ingredientsCategoryRepo.findById(id);
        if(ing_op.isEmpty()) throw  new Exception("ingredient category not found");
        return ing_op.get();
    }

    @Override
    public List<IngredientCategory> findIngredientsCategoryByRestaurantId(Long id) throws Exception {
        return  ingredientsCategoryRepo.findRestaurantId(id);
    }

    @Override
    public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) {
        return  ingredientsItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem createIngredientsItem(Long restaurantId, String ingredientName, Long ingredientCategoryId) throws Exception {
        IngredientCategory category = findIngredientsCategoryById(ingredientCategoryId);
        IngredientsItem isExist = ingredientsItemRepository.findByRestaurantIdAndNameIngoreCase(restaurantId, ingredientName, category.getName());
        if(isExist != null) return isExist;

        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientsItem item = new IngredientsItem();
        item.setName(ingredientName);
        item.setRestaurant(restaurant);
        item.setCategory(category);

        IngredientsItem savedIngredients = ingredientsItemRepository.save(item);
        category.getIngredients().add(savedIngredients);
        return  savedIngredients;
    }


    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> item=ingredientsItemRepository.findById(id);
        if(item.isEmpty()) {
            throw new Exception("ingredient not found with id "+item);
        }
        IngredientsItem ingredient=item.get();
        ingredient.setInStoke(!ingredient.isInStoke());
        return ingredientsItemRepository.save(ingredient);
    }
}
