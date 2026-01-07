package com.cyro.craveKart.service;

import java.util.List;

//import com.cstripe.exception.StripeException;
import com.cyro.craveKart.exceptions.CartException;
import com.cyro.craveKart.exceptions.OrderException;
import com.cyro.craveKart.exceptions.RestaurantException;
import com.cyro.craveKart.exceptions.UserException;
import com.cyro.craveKart.model.Order;
import com.cyro.craveKart.model.PaymentResponse;
import com.cyro.craveKart.model.User;
import com.cyro.craveKart.request.CreateOrderRequest;
import com.stripe.exception.StripeException;

public interface OrderService {
	
	 public PaymentResponse createOrder(CreateOrderRequest order, User user) throws UserException, RestaurantException, CartException, StripeException;
	 
	 public Order updateOrder(Long orderId, String orderStatus) throws OrderException;
	 
	 public void cancelOrder(Long orderId) throws OrderException;
	 
	 public List<Order> getUserOrders(Long userId) throws OrderException;
	 
	 public List<Order> getOrdersOfRestaurant(Long restaurantId,String orderStatus) throws OrderException, RestaurantException;
	 

}
