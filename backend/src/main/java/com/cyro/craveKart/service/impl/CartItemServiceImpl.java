package com.cyro.cravekart.service.impl;

import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.config.security.AuthService;
import com.cyro.cravekart.dto.CartItemDto;
import com.cyro.cravekart.models.Cart;
import com.cyro.cravekart.models.CartItem;
import com.cyro.cravekart.models.Customer;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.repository.CartItemRepository;
import com.cyro.cravekart.response.CartItemResponse;
import com.cyro.cravekart.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

  private final CartItemRepository cartItemRepository;
  private final ModelMapper modelMapper;
  private final AuthContextService authContextService;

  @Override
  public CartItem getCartItemById(Long cartItemId) {
   return cartItemRepository.findById(cartItemId).orElseThrow(
        ()-> new RuntimeException("Cart Item with id " + cartItemId + " not found")
    );
  }

  @Override
  public CartItemResponse incrementCartItemQuantity(CartItem cartItem, Integer quantity) {
    cartItem.setQuantity(cartItem.getQuantity() + quantity);
    Cart cart = cartItem.getCart();
    cartItem.setCart(cart);
    return modelMapper.map(cartItemRepository.save(cartItem),
        CartItemResponse.class);

  }

  @Override
  public CartItemResponse decrementCartItemQuantity(CartItem cartItem, Integer quantity) {
    cartItem.setQuantity(cartItem.getQuantity() - quantity);
    Cart cart = cartItem.getCart();
    cartItem.setCart(cart);
    return  modelMapper.map(cartItemRepository.save(cartItem), CartItemResponse.class);
  }

  @Override
  public void removeCartItemFromCart(CartItem cartItem) throws BadRequestException {

    Customer customer = authContextService.getCustomer();

    Cart cart = cartItem.getCart();
    if(!cart.getItems().contains(cartItem)) {
      throw  new BadRequestException("Cart item with id: " + cartItem.getId() +
          "is not associated with customer with id : " +  customer.getId());
    }
    cartItemRepository.deleteById(cartItem.getId());
  }


}
