package com.cyro.cravekart.service;

import com.cyro.cravekart.dto.CartItemDto;
import com.cyro.cravekart.models.CartItem;
import com.cyro.cravekart.response.CartItemResponse;
import com.cyro.cravekart.response.CartResponse;
import org.apache.coyote.BadRequestException;

public interface CartItemService {
  CartItem getCartItemById(Long cartItemId);
  CartResponse incrementCartItemQuantity(Long cartItemId);
  CartResponse  decrementCartItemQuantity(Long cartItemId);
  CartResponse removeCartItemFromCart(Long cartItemId ) ;
}
