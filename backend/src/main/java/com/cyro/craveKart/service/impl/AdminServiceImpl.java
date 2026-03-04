package com.cyro.cravekart.service.impl;

import com.cyro.cravekart.exception.ResourceNotFoundException;
import com.cyro.cravekart.models.Restaurant;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.models.enums.RestaurantStatus;
import com.cyro.cravekart.repository.OrderRepository;
import com.cyro.cravekart.repository.RestaurantRepository;
import com.cyro.cravekart.repository.UserRepository;
import com.cyro.cravekart.request.USER_STATUS;
import com.cyro.cravekart.response.OrderResponse;
import com.cyro.cravekart.response.RestaurantResponse;
import com.cyro.cravekart.response.UserResponse;
import com.cyro.cravekart.service.AdminService;
import com.cyro.cravekart.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
  private final UserService userService;
  private final UserRepository userRepository;
  private final RestaurantRepository restaurantRepository;
  private final OrderRepository orderRepository;

  @Override
  public List<UserResponse> getAllUsers() {
    return  userService.findAllUsers();
  }

  @Override
  public void blockUser(Long userId) {

    User user = userRepository.findById(userId).orElseThrow(
        () -> new ResourceNotFoundException("User not found with id: " + userId)
    );

    user.setStatus(USER_STATUS.BLOCKED);
    userRepository.save(user);

  }

  @Override
  public void unblockUser(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() ->
            new ResourceNotFoundException("User not found"));

    user.setStatus(USER_STATUS.ACTIVE);
    userRepository.save(user);
  }


  // ================= RESTAURANTS =================


  @Override
  public List<RestaurantResponse> getPendingRestaurants() {

    return restaurantRepository
        .findByStatus(RestaurantStatus.PENDING)
        .stream()
        .map(RestaurantResponse::from)
        .toList();
  }

  @Override
  public void approveRestaurant(Long restaurantId) {
    Restaurant restaurant = restaurantRepository.findById(restaurantId)
        .orElseThrow(() ->
            new ResourceNotFoundException("Restaurant not found"));

    restaurant.setStatus(RestaurantStatus.APPROVED);
    restaurantRepository.save(restaurant);
  }

  @Override
  public void suspendRestaurant(Long restaurantId) {
    Restaurant restaurant = restaurantRepository.findById(restaurantId)
        .orElseThrow(() ->
            new ResourceNotFoundException("Restaurant not found"));

    restaurant.setStatus(RestaurantStatus.SUSPENDED);
    restaurantRepository.save(restaurant);
  }

  // ================= ORDERS =================

  @Override
  public List<OrderResponse> getAllOrders() {

    return orderRepository.findAll()
        .stream()
        .map(OrderResponse::from)
        .toList();
  }
}
