package com.cyro.craveKart.service;

import com.cyro.craveKart.dto.RestaurantDTO;
import com.cyro.craveKart.exception.RestaurantException;
import com.cyro.craveKart.model.Restaurant;
import com.cyro.craveKart.model.User;
import com.cyro.craveKart.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {
    public Restaurant createRestaurant(CreateRestaurantRequest createRestaurantRequest, User user);
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurant) throws RestaurantException;
    public void deleteRestaurant(Long restaurantId) throws RestaurantException;
    public List<Restaurant> getAllRestaurant();

    List<Restaurant> searchRestaurant(String keyword);

    public Restaurant findRestaurantById(Long id) throws RestaurantException;

    public Restaurant getRestaurantByUserId(Long userId) throws RestaurantException;

    public RestaurantDTO addtoFavourites(Long restaurantId, User user) throws RestaurantException;

    public Restaurant updateRestaurantStatus(Long id) throws RestaurantException;

    Restaurant findRestaurantByUserId(Long id);
}
