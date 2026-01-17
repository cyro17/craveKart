package com.cyro.cravekart.service;

import com.cyro.cravekart.request.AddCartItemRequest;
import com.cyro.cravekart.response.CartResponse;

public interface CartService {
  public CartResponse getCart();
  public CartResponse addItem(AddCartItemRequest request);

  void clearCart();

  CartResponse updateQuantity(Long cartItemId, Integer quantity);

  void removeItem(Long cartItemId);
}
