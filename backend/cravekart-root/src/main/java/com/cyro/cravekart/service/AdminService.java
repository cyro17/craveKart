package com.cyro.cravekart.service;

import com.cyro.cravekart.response.OrderResponse;
import com.cyro.cravekart.response.RestaurantResponse;
import com.cyro.cravekart.response.UserResponse;

import java.util.List;

public interface AdminService {

  List<UserResponse> getAllUsers();

  void blockUser(Long userId);

  void unblockUser(Long userId);

  List<RestaurantResponse> getPendingRestaurants();

  void approveRestaurant(Long restaurantId);

  void suspendRestaurant(Long restaurantId);

  List<OrderResponse> getAllOrders();
}
