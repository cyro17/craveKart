package com.cyro.craveKart.service;

import com.cyro.craveKart.exception.CartException;
import com.cyro.craveKart.exception.OrderException;
import com.cyro.craveKart.exception.RestaurantException;
import com.cyro.craveKart.exception.UserException;
import com.cyro.craveKart.model.Order;
import com.cyro.craveKart.model.PaymentResponse;
import com.cyro.craveKart.model.User;
import com.cyro.craveKart.request.OrderRequest;

import java.util.List;

public interface OrderService {

    public PaymentResponse createOrder(OrderRequest order, User user)
            throws UserException, RestaurantException, CartException;

    public Order updateOrder(Long orderId, String orderStatus)
            throws OrderException;

    public void cancelOrder(Long orderId)
            throws OrderException;

    public List<Order> getUserOrders(Long userId)
            throws OrderException;

    public List<Order> getOrdersOfRestaurant(Long restaurantId,String orderStatus)
            throws OrderException, RestaurantException;

}
