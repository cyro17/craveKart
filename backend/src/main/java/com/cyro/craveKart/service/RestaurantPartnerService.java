package com.cyro.cravekart.service;

import com.cyro.cravekart.models.Order;
import com.cyro.cravekart.models.RestaurantPartner;
import com.cyro.cravekart.models.User;
import org.apache.coyote.BadRequestException;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface RestaurantPartnerService {

  public Order acceptOrder(Long orderId) throws AccessDeniedException;
  public Order rejectOrder(Long orderId) throws AccessDeniedException, BadRequestException;
  public List<Order> getPendingOrdersForRestaurants(Long restaurantId) throws AccessDeniedException, BadRequestException;

  RestaurantPartner getById(Long restaurantPartnerId) throws AccessDeniedException, BadRequestException;
  List<Order> getNewOrders();
  public RestaurantPartner createRestaurantPartner(User user);

  void apply(RestaurantPartner restaurantPartner);

  void getStatus(RestaurantPartner restaurantPartner);
}
