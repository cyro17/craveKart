package com.cyro.cravekart.service;

import com.cyro.cravekart.dto.CartItemDto;
import com.cyro.cravekart.models.CartItem;
import com.cyro.cravekart.response.CartItemResponse;
import org.apache.coyote.BadRequestException;

public interface CartItemService {
  CartItem getCartItemById(Long cartItemId);
  CartItemResponse incrementCartItemQuantity(CartItem cartItem, Integer quantity);
  CartItemResponse  decrementCartItemQuantity(CartItem cartItem, Integer quantity);
  void removeCartItemFromCart(CartItem cartItem) throws BadRequestException;
}
