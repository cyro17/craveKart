package com.cyro.cravekart.service;


import com.cyro.cravekart.dto.RestaurantDto;
import com.cyro.cravekart.exception.RestaurantException;
import com.cyro.cravekart.models.Restaurant;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.request.CreateRestaurantRequest;
import com.cyro.cravekart.response.CreateRestaurantResponse;

import java.util.List;

public interface RestaurantService {

  public CreateRestaurantResponse createRestaurant(CreateRestaurantRequest req, User user);

  public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant)
      throws RestaurantException;

  public void deleteRestaurant(Long restaurantId) throws RestaurantException;

  public List<Restaurant> getAllRestaurant();

  public List<Restaurant>searchRestaurant(String keyword);

  public Restaurant findRestaurantById(Long id) throws RestaurantException;

  public List<Restaurant> getRestaurantsByUserId(Long userId) throws RestaurantException;

  public RestaurantDto addToFavorites(Long restaurantId, User user) throws RestaurantException;

  public Restaurant updateRestaurantStatus(Long id)throws RestaurantException;

}
