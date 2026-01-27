package com.cyro.cravekart.service;

import com.cyro.cravekart.exception.OrderException;
import com.cyro.cravekart.exception.RestaurantException;
import com.cyro.cravekart.models.Order;
import com.cyro.cravekart.response.PlaceOrderResponse;
import org.apache.coyote.BadRequestException;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface OrderService {
  PlaceOrderResponse placeOrder();
  public  Order updateOrder(Long orderId, String OrderStatus);
  public void cancelOrder(Long orderId) throws AccessDeniedException, BadRequestException;
  List<Order> getUserOrders(Long id);
  List<Order> getOrdersOfRestaurant(Long restaurantId, String orderStatus) throws OrderException, RestaurantException;
}
