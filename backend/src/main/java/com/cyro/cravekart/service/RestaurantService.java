package com.cyro.cravekart.service;


import com.cyro.cravekart.dto.RestaurantDto;
import com.cyro.cravekart.models.Customer;
import com.cyro.cravekart.models.Restaurant;
import com.cyro.cravekart.request.CreateRestaurantRequest;
import com.cyro.cravekart.request.OnboardRestaurantRequest;
import com.cyro.cravekart.response.CreateRestaurantResponse;
import com.cyro.cravekart.response.OnboardRestaurantResponse;
import com.cyro.cravekart.response.RestaurantMenuResponse;
import com.cyro.cravekart.response.RestaurantResponse;

import java.util.List;

public interface RestaurantService {

  // ADMIN
  public OnboardRestaurantResponse onboardRestaurant(OnboardRestaurantRequest req, Long restaurantId) ;
  public CreateRestaurantResponse createRestaurant(CreateRestaurantRequest req) ;

  public List<RestaurantResponse> getAllRestaurant(int pageNo, int pageSize);

  public List<RestaurantResponse> getRestaurantsByFilter(String city,
                                                         String cuisine,
                                                         Double rating,
                                                         String sort,
                                                         int page,
                                                         int size);

  public RestaurantMenuResponse getRestaurantMenu(Long restaurantId) ;

  public RestaurantResponse updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant);

  public void deleteRestaurant(Long restaurantId) ;

  boolean assignPartner(Long restaurantId, Long partnerId) ;

  // partner
  public Restaurant getRestaurantByPartnerId(Long partnerId) ;


  // customer
  public List<RestaurantResponse>searchRestaurant(String keyword);

  public RestaurantResponse getRestaurantById(Long id) ;

  public RestaurantDto addToFavorites(Long restaurantId, Customer customer) ;


  public RestaurantResponse updateRestaurantStatus(Long id);

  public Restaurant getRestaurantById_util(Long id);

}
