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
   // restaurant

  Order updatePreparationStatus(Long orderId, OrderStatus orderStatus);
  Order pickupOrder(Long orderId) ; // delivery
  Order deliveryOrder(Long orderId);

  String cancelOrder(Long orderId) ;

  List<OrderResponse> getCustomerOrders(Long id);
  OrderResponse getOrderById(Long orderId);
  List<Order> getOrdersOfRestaurant(Long restaurantId, String orderStatus);
  public List<Order> getPendingOrdersForRestaurant();


  void markAsConfirmed(Long orderId) ;
  void markAsPaid(Long orderId);
  void markAsFailed(Long orderId);
}
