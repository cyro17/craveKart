package com.cyro.craveKart.service;

import com.cyro.craveKart.exception.FoodException;
import com.cyro.craveKart.model.Category;
import com.cyro.craveKart.model.Food;
import com.cyro.craveKart.model.Restaurant;
import com.cyro.craveKart.repository.FoodRepository;
import com.cyro.craveKart.repository.IngredientsCategoryRepository;
import com.cyro.craveKart.request.CreateFoodRequest;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl  implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private IngredientsService ingredientService;

    @Autowired
    private IngredientsCategoryRepository ingredientCategoryRepo;

    @Override
    public Food createFood(CreateFoodRequest createFoodRequest,
                           Category category,
                           Restaurant restaurant) throws FoodException {

        Food food=new Food();
        food.setFoodCategory(category);
        food.setCreationDate(new Date());
        food.setDescription(createFoodRequest.getDescription());
        food.setImages(createFoodRequest.   getImages());
        food.setName(createFoodRequest.getName());
        food.setPrice((long) createFoodRequest.getPrice());
        food.setSeasonal(createFoodRequest.isSeasonal());
        food.setVegetarian(createFoodRequest.isVegetarian());
        food.setIngredients(createFoodRequest.getIngredients());
        food.setRestaurant(restaurant);
        food = foodRepository.save(food);

        restaurant.getFoods().add(food);
        return food;
    }

    @Override
    public void deleteFood(ObjectId foodId) throws FoodException {
        Food foodById = findFoodById(foodId);
        foodById.setRestaurant(null);
        foodRepository.delete(foodById);
    }

    @Override
    public List<Food> getRestaurantsFood(ObjectId restaurantId,
                                         boolean isVeg,
                                         boolean isNonVeg,
                                         boolean isSeasonal,
                                         String foodCategory) throws FoodException {
        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);

        if(isVeg) foods = filterByVegetarian(foods, isVeg);
        if(isNonVeg) foods = filterByNonVegetarian(foods, isNonVeg);
        if(isSeasonal) foods = filterBySeasonal(foods, isSeasonal);

        if(foodCategory != null && !foodCategory.equals("")){
            foods = filterByFoodCategory(foods, foodCategory);
        }
        return  foods;
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVeg){
        return foods.stream()
                .filter(food -> food.isVegetarian() == isVeg)
                .collect(Collectors.toList());
    }

    private List<Food> filterByNonVegetarian(List<Food> foods, boolean isNonVeg){
        return foods.stream()
                .filter(food -> food.isVegetarian() == false)
                .collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal){
        return foods.stream()
                .filter(food -> food.isSeasonal() == true)
                .collect(Collectors.toList());
    }

    private List<Food> filterByFoodCategory(List<Food> foods, String foodCategory){
        return  foods.stream()
                .filter(food -> {
                    if(food.getFoodCategory() != null){
                        return food.getFoodCategory().getName().equals(foodCategory);
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String food) {
        List<Food> items = new ArrayList<>();
        if(food != ""){
            items = foodRepository.searchByNameOrCategory(food);
        }
        return  items;
    }

    @Override
    public Food findFoodById(ObjectId foodId) throws FoodException {
        Optional<Food> food = foodRepository.findById(foodId);
        if(food.isPresent()) return food.get();
        throw new FoodException("Food with id : " + foodId + "not found ");
    }

    @Override
    public Food updateAvalibilityStatus(ObjectId foodId) throws FoodException {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        foodRepository.save(food);
        return food;
    }
}
