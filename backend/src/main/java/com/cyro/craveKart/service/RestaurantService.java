package com.cyro.cravekart.service;


import com.cyro.cravekart.dto.RestaurantDto;
import com.cyro.cravekart.exception.RestaurantException;
import com.cyro.cravekart.models.Customer;
import com.cyro.cravekart.models.Restaurant;
import com.cyro.cravekart.models.RestaurantPartner;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.request.CreateRestaurantRequest;
import com.cyro.cravekart.response.CreateRestaurantResponse;
import com.cyro.cravekart.response.RestaurantResponse;

import java.util.List;

public interface RestaurantService {

  // ADMIN
  public CreateRestaurantResponse createRestaurant(CreateRestaurantRequest req, RestaurantPartner partner) throws RestaurantException;

  public List<RestaurantResponse> getAllRestaurant();

  public RestaurantResponse updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant)
      throws RestaurantException;

  public void deleteRestaurant(Long restaurantId) throws RestaurantException;

  void assignPartner(Long restaurantId, Long partnerId) throws RestaurantException;

  // partner
  public Restaurant getRestaurantByPartnerId(Long partnerId) throws RestaurantException;


  // customer
  public List<RestaurantResponse>searchRestaurant(String keyword);

  public RestaurantResponse getRestaurantById(Long id) throws RestaurantException;

  public RestaurantDto addToFavorites(Long restaurantId, Customer customer) throws RestaurantException;


  public RestaurantResponse updateRestaurantStatus(Long id)throws RestaurantException;

  public Restaurant getRestaurantById_util(Long id);

}
