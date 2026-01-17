package com.cyro.cravekart.service;

import com.cyro.cravekart.exception.OrderException;
import com.cyro.cravekart.exception.RestaurantException;
import com.cyro.cravekart.models.Order;
import com.cyro.cravekart.request.CreateOrderRequest;
import com.cyro.cravekart.response.PlaceOrder;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

public interface OrderService {
  PlaceOrder placeOrder();
  public  Order updateOrder(Long orderId, String OrderStatus);
  public void cancelOrder(Long orderId);
  List<Order> getUserOrders(Long id);
  List<Order> getOrdersOfRestaurant(Long restaurantId, String orderStatus) throws OrderException, RestaurantException;
}
