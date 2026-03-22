package com.cyro.cravekart.service;

import com.cyro.cravekart.models.enums.OnboardingStatus;
import com.cyro.cravekart.response.OrderResponse;
import com.cyro.cravekart.response.RestaurantPartnerResponse;
import com.cyro.cravekart.response.RestaurantResponse;
import com.cyro.cravekart.response.UserResponse;
import java.util.List;

public interface AdminService {

  // users
  List<UserResponse> getAllUsers();

  void blockUser(Long userId);

  void unblockUser(Long userId);

  // restaurants
  List<RestaurantResponse> getPendingRestaurants();

  void approveRestaurant(Long restaurantId);

  void suspendRestaurant(Long restaurantId);

  // partners
  List<RestaurantPartnerResponse> getPendingRestaurantPartners();

  RestaurantPartnerResponse getPendingRestaurantPartner(Long partnerId);

  void approvePartner(Long partnerId);

  void rejectPartner(Long partnerId, String reason);

  void suspendPartner(Long partnerId, String reason);

  void reactivatePartner(Long partnerId);

  List<RestaurantPartnerResponse> getPartnersByStatus(OnboardingStatus status);

  // orders
  List<OrderResponse> getAllOrders();
}
