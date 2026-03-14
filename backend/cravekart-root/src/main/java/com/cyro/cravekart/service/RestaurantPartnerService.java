package com.cyro.cravekart.service;

import com.cyro.cravekart.models.Order;
import com.cyro.cravekart.models.RestaurantPartner;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.response.RestaurantOrderSummary;
import org.apache.coyote.BadRequestException;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface RestaurantPartnerService {

  public List<RestaurantOrderSummary> getNewOrders();
  public List<RestaurantOrderSummary> getActiveOrders();
  public List<RestaurantOrderSummary> getOrderHistory();
  public RestaurantOrderSummary getOrderDetail(Long OrderId);

  public RestaurantOrderSummary acceptOrder(Long orderId);
  public RestaurantOrderSummary markPreparing(Long orderId);
  public RestaurantOrderSummary markReadyForPickUp(Long orderId);

  public void rejectOrder(Long orderId, String reason);

  public List<RestaurantOrderSummary> getPendingOrdersForRestaurants(Long restaurantId) ;


  /* === ADMIN Method ===== */
  public RestaurantPartner createRestaurantPartner(User user);

  RestaurantPartner getById(Long restaurantPartnerId) ;

  void apply(RestaurantPartner restaurantPartner);
//  public RestaurantOrderSummary getOrderDetail(Long orderId);
//
  void getStatus(RestaurantPartner restaurantPartner);
}
