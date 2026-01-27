package com.cyro.cravekart.service.impl;

import com.cyro.cravekart.config.security.AuthService;
import com.cyro.cravekart.dto.*;
import com.cyro.cravekart.exception.FoodException;
import com.cyro.cravekart.request.AddCartItemRequest;
import com.cyro.cravekart.response.CartResponse;
import com.cyro.cravekart.response.OrderResponse;
import com.cyro.cravekart.service.CartService;
import com.cyro.cravekart.service.CustomerService;
import com.cyro.cravekart.service.FoodService;
import com.cyro.cravekart.service.RestaurantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.naming.ServiceUnavailableException;

@Service
@RequiredArgsConstructor
public class CutomerServiceImpl implements CustomerService {
  private final FoodService foodService;
  private final RestaurantService restaurantService;
  private final AuthService authService;
  private final CartService cartService;

  @Override
  @Transactional
  public CartResponse addFoodToCart(Long foodId) throws FoodException, ServiceUnavailableException {
    AddCartItemRequest cartItemRequest = AddCartItemRequest.builder().foodId(foodId)
        .quantity(1).build();
    return cartService.addItem(cartItemRequest);
  }

  @Override
  public CartItemDto incrementCartItemQuantity(Long cartItemId, Long cartId, Integer quantity) {
    authService.getCurrentAuthUser();
    return  null;
  }

  @Override
  public CartItemDto decrementCartItemQuantity(Long cartItemId, Long cartId, Integer quantity) {
    return null;
  }

  @Override
  public CartDto removeCartItemFromCart(Long cartItemId, Long cartId) {
    return null;
  }

  @Override
  public void deletecart(Long cartId) {

  }

  @Override
  public OrderResponse placeOrder() {
    return null;
  }

  @Override
  public OrderResponse cancelOrder(Long OrderId) {
    return null;
  }

  @Override
  public Page<CustomerOrderDTO> getAllMyOrders(PageRequest pageRequest) {
    return null;
  }

  @Override
  public CustomerDto getMyProfile() {
    return null;
  }

  @Override
  public CartDto getCartById(Long cartId) {
    return null;
  }

  @Override
  public Page<RestaurantDto> getAllNearByRestaurants(PageRequest pageRequest) {
    return null;
  }
}
