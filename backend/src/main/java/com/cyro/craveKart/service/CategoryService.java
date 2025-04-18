package com.cyro.craveKart.service;

import com.cyro.craveKart.exception.RestaurantException;
import com.cyro.craveKart.model.Category;

import java.util.List;

public interface CategoryService {
    public Category createCategory(String name, Long userId) throws RestaurantException;
    public List<Category> findCategoryByRestaurantId(Long restaurantId) throws RestaurantException;
    public Category findCategoryById(Long id) throws RestaurantException;
}
