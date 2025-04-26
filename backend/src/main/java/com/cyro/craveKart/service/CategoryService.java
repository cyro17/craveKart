package com.cyro.craveKart.service;

import com.cyro.craveKart.exception.RestaurantException;
import com.cyro.craveKart.model.Category;
import org.bson.types.ObjectId;

import java.util.List;

public interface CategoryService {
    public Category createCategory(String name, ObjectId userId) throws RestaurantException;
    public List<Category> findCategoryByRestaurantId(ObjectId restaurantId) throws RestaurantException;
    public Category findCategoryById(ObjectId id) throws RestaurantException;
}
