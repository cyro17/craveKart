package com.cyro.craveKart.service;

import com.cyro.craveKart.exception.RestaurantException;
import com.cyro.craveKart.model.Category;
import com.cyro.craveKart.model.Restaurant;
import com.cyro.craveKart.repository.CategoryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private RestaurantServiceImpl restaurantService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(String name, ObjectId userId) throws RestaurantException {
        Restaurant restaurant=restaurantService.getRestaurantByUserId(userId);
        Category createdCategory=new Category();

        createdCategory.setName(name);
        createdCategory.setRestaurant(restaurant);
        return categoryRepository.save(createdCategory);
    }


    @Override
    public List<Category> findCategoryByRestaurantId(ObjectId restaurantId) throws RestaurantException {
        return  categoryRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public Category findCategoryById(ObjectId id) throws RestaurantException {
        Optional<Category> byId = categoryRepository.findById(id);
        if(byId.isEmpty()) throw new RestaurantException("category does not exist with id : " + id);
        return byId.get();
    }
}
