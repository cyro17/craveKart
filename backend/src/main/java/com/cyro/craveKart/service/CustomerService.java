package com.cyro.cravekart.service;

import com.cyro.cravekart.dto.*;
import com.cyro.cravekart.exception.FoodException;
import com.cyro.cravekart.response.CartResponse;
import com.cyro.cravekart.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.naming.ServiceUnavailableException;

public interface CustomerService {

  CartResponse addFoodToCart(Long foodItemId) throws FoodException, ServiceUnavailableException;
  CartItemDto incrementCartItemQuantity(Long cartItemId, Long cartId, Integer quantity);
  CartItemDto decrementCartItemQuantity(Long cartItemId, Long cartId, Integer quantity);
  CartDto removeCartItemFromCart(Long cartItemId, Long cartId);

  void deletecart(Long cartId);
  OrderResponse placeOrder();
  OrderResponse cancelOrder(Long OrderId);

  Page<CustomerOrderDTO> getAllMyOrders(PageRequest pageRequest);

  CustomerDto getMyProfile();

  CartDto getCartById(Long cartId);

  Page<RestaurantDto> getAllNearByRestaurants(PageRequest pageRequest);





}
