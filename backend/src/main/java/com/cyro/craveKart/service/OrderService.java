package com.cyro.cravekart.service;

import com.cyro.cravekart.exception.OrderException;
import com.cyro.cravekart.exception.RestaurantException;
import com.cyro.cravekart.models.Order;
import com.cyro.cravekart.models.enums.OrderStatus;
import com.cyro.cravekart.request.PlaceOrderRequest;
import com.cyro.cravekart.response.OrderResponse;
//import com.cyro.cravekart.response.PlaceOrderResponse;
import org.apache.coyote.BadRequestException;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface OrderService {
  OrderResponse placeOrder(PlaceOrderRequest request);
  Order confirmOrder(Long orderId) throws AccessDeniedException, BadRequestException; // restaurant
  Order updatePreparationStatus(Long orderId, OrderStatus orderStatus);
  Order pickupOrder(Long orderId) throws BadRequestException; // delivery
  Order deliveryOrder(Long orderId);

  String cancelOrder(Long orderId) throws AccessDeniedException, BadRequestException;

  List<OrderResponse> getCustomerOrders(Long id);
  List<Order> getOrdersOfRestaurant(Long restaurantId, String orderStatus) throws OrderException, RestaurantException;
  public List<Order> getPendingOrdersForRestaurant();

  void markAsPaid(Long orderId);
  void markAsFailed(Long orderId);
}
