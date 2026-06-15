package com.cyro.cravekart.service;

import com.cyro.cravekart.dto.*;
import com.cyro.cravekart.request.AddressRequest;
import com.cyro.cravekart.response.AddressResponse;
import com.cyro.cravekart.response.CartResponse;
import com.cyro.cravekart.response.OrderResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CustomerService {

  CartResponse addFoodToCart(Long foodItemId);
  CartItemDto incrementCartItemQuantity(Long cartItemId, Long cartId, Integer quantity);
  CartItemDto decrementCartItemQuantity(Long cartItemId, Long cartId, Integer quantity);
  CartDto removeCartItemFromCart(Long cartItemId, Long cartId);

  void deletecart(Long cartId);
  OrderResponse placeOrder();
  OrderResponse cancelOrder(Long OrderId);

  Page<CustomerOrderDTO> getAllMyOrders(PageRequest pageRequest);

  CustomerDto getMyProfile();
  CustomerDto getCustomerById(Long customerId );

  CartDto getCartById(Long cartId);

  Page<RestaurantDto> getAllNearByRestaurants(PageRequest pageRequest);

  AddressResponse saveAddress(AddressRequest createAddressRequest);
  List<AddressResponse> getUserAddress();





}
