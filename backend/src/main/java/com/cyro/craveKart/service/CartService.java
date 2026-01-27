package com.cyro.cravekart.service;

import com.cyro.cravekart.models.Cart;
import com.cyro.cravekart.request.AddCartItemRequest;
import com.cyro.cravekart.response.CartResponse;
import org.apache.coyote.BadRequestException;

import java.util.Optional;

public interface CartService {
  public CartResponse getCart();
  public CartResponse addItem(AddCartItemRequest request);

  CartResponse updateQuantity(Long cartItemId, Integer quantity);

  void removeCartItem(Long cartItemId) throws BadRequestException;

  public Cart getCartByUserId(Long userId);

  void clearCart();


}
