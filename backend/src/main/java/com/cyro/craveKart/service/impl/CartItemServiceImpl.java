package com.cyro.cravekart.service.impl;

import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.config.security.AuthService;
import com.cyro.cravekart.dto.CartItemDto;
import com.cyro.cravekart.models.Cart;
import com.cyro.cravekart.models.CartItem;
import com.cyro.cravekart.models.Customer;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.repository.CartItemRepository;
import com.cyro.cravekart.repository.CartRepository;
import com.cyro.cravekart.response.CartItemResponse;
import com.cyro.cravekart.response.CartResponse;
import com.cyro.cravekart.service.CartItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class CartItemServiceImpl implements CartItemService {

  private final CartItemRepository cartItemRepository;
  private final ModelMapper modelMapper;
  private final AuthContextService authContextService;
  private final CartRepository cartRepository;

  @Override
  public CartItem getCartItemById(Long cartItemId) {
   return cartItemRepository.findById(cartItemId).orElseThrow(
        ()-> new RuntimeException("Cart Item with id " + cartItemId + " not found")
    );
  }

  @Override
  public CartResponse incrementCartItemQuantity(Long cartItemId) {
    CartItem cartItem = validateCartaItemOwnership(cartItemId);

    cartItem.setQuantity(cartItem.getQuantity() + 1);
    updateItemTotal(cartItem);

    cartItemRepository.save(cartItem);

    Cart cart = cartItem.getCart();
    recalculateCartTotal(cart);
    cartRepository.save(cart);

    return buildCartResponse(cart);

  }

  @Override
  public CartResponse decrementCartItemQuantity(Long cartItemId) {
    CartItem cartItem = validateCartaItemOwnership(cartItemId);
    if(cartItem.getQuantity() <= 1) {
      cartItemRepository.delete(cartItem);
    }else {
      cartItem.setQuantity(cartItem.getQuantity() - 1);
      updateItemTotal(cartItem);
      cartItemRepository.save(cartItem);
    }

    Cart cart = cartItem.getCart();
    recalculateCartTotal(cart);
    cartRepository.save(cart);
    return buildCartResponse(cart);
  }

  @Override
  public CartResponse removeCartItemFromCart(Long CartItemId){

    CartItem cartItem = validateCartaItemOwnership(CartItemId);
    Cart cart = cartItem.getCart();
    cart.getItems().remove(cartItem);

    cartItemRepository.delete(cartItem);

    recalculateCartTotal(cart);
    cartRepository.save(cart);

    return buildCartResponse(cart);

  }


//  Helper method

  private CartItem validateCartaItemOwnership(Long cartItemId){
    Customer customer = authContextService.getCustomer();
    CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(
        () -> new RuntimeException("Cart Item with id " + cartItemId + " not found")
    );
    if(!cartItem.getCart().getCustomer().getId().equals(customer.getId())) {
      throw  new RuntimeException("Unauthorized cart access");
    }
    return cartItem;
  }

  private void updateItemTotal(CartItem cartItem) {
    cartItem.setTotalPrice(
        cartItem.getFood().getPrice().multiply(
            BigDecimal.valueOf(cartItem.getQuantity())));

  }

  private void recalculateCartTotal(Cart cart) {
    BigDecimal total = cart.getItems().stream()
        .map(CartItem::getTotalPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    cart.setCartTotal(total);
  }

  private CartResponse buildCartResponse(Cart cart) {
    CartResponse cartResponse = new CartResponse();
    cartResponse.setCartId(cart.getId());
    cartResponse.setCartTotal(cart.getCartTotal());

    List<CartItemResponse> items = cart.getItems().stream()
        .map(this::mapToCartItemResponse)
         .toList();
    cartResponse.setItems(items);
    return cartResponse;
  }

  private CartItemResponse mapToCartItemResponse(CartItem cartItem) {
    return CartItemResponse.builder()
        .cartItemId(cartItem.getId())
        .foodId(cartItem.getFood().getId())
        .foodName(cartItem.getFood().getName())
        .description(cartItem.getFood().getDescription())
        .quantity(cartItem.getQuantity())
        .totalPrice(cartItem.getTotalPrice())
        .restaurantName(cartItem.getFood().getRestaurant().getName())
        .images(cartItem.getFood().getImages())
        .build();

  }

}
