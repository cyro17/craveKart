package com.cyro.cravekart.service;

import com.cyro.cravekart.models.Order;
import com.cyro.cravekart.models.RestaurantPartner;
import com.cyro.cravekart.models.User;
import org.apache.coyote.BadRequestException;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface RestaurantPartnerService {

  public Order acceptOrder(Long orderId);
  public Order rejectOrder(Long orderId) ;
  public List<Order> getPendingOrdersForRestaurants(Long restaurantId) ;

  RestaurantPartner getById(Long restaurantPartnerId) ;
  List<Order> getNewOrders();
  public RestaurantPartner createRestaurantPartner(User user);

  void apply(RestaurantPartner restaurantPartner);

  void getStatus(RestaurantPartner restaurantPartner);
}
