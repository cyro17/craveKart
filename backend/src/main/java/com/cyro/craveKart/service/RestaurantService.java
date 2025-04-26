package com.cyro.craveKart.service;

import com.cyro.craveKart.dto.RestaurantDTO;
import com.cyro.craveKart.exception.RestaurantException;
import com.cyro.craveKart.model.Restaurant;
import com.cyro.craveKart.model.User;
import com.cyro.craveKart.request.CreateRestaurantRequest;
import org.bson.types.ObjectId;

import java.util.List;

public interface RestaurantService {
    public Restaurant createRestaurant(CreateRestaurantRequest createRestaurantRequest, User user);
    public Restaurant updateRestaurant(ObjectId restaurantId, CreateRestaurantRequest updateRestaurant) throws RestaurantException;
    public void deleteRestaurant(ObjectId restaurantId) throws RestaurantException;
    public List<Restaurant> getAllRestaurant();

    List<Restaurant> searchRestaurant(String keyword);

    public Restaurant findRestaurantById(ObjectId id) throws RestaurantException;

    public Restaurant getRestaurantByUserId(ObjectId userId) throws RestaurantException;

    public RestaurantDTO addtoFavourites(ObjectId restaurantId, User user) throws RestaurantException;

    public Restaurant updateRestaurantStatus(ObjectId id) throws RestaurantException;

    Restaurant findRestaurantByUserId(ObjectId id);
}
