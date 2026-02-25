package com.cyro.cravekart.service.impl;

import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.config.security.AuthService;
import com.cyro.cravekart.models.*;
import com.cyro.cravekart.repository.CartItemRepository;
import com.cyro.cravekart.repository.CartRepository;
import com.cyro.cravekart.repository.FoodRepository;
import com.cyro.cravekart.request.AddCartItemRequest;
import com.cyro.cravekart.response.CartItemResponse;
import com.cyro.cravekart.response.CartResponse;
import com.cyro.cravekart.service.CartItemService;
import com.cyro.cravekart.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

  private final CartRepository cartRepository;
  private final AuthContextService authService;
  private final FoodRepository foodRepository;
  private final CartItemRepository cartItemRepository;
  private final CartItemService cartItemService;

  @Override
  public CartResponse getCart() {
    Cart cart = getCartOrCreateNew();
    return mapToCartResponse(cart);
  }

  @Override
  public CartResponse addItem(AddCartItemRequest request) {
    Customer user = authService.getCustomer();
    Cart cart = getCartOrCreateNew();

    Food food = foodRepository.findById(request.getFoodId())
        .orElseThrow(() -> new RuntimeException("Food not found"));

    String restaurantName = food.getRestaurant().getName();
    List<String> images = food.getImages();

    Optional<CartItem> existing = cart.getItems().stream()
        .filter(item -> item.getFood().getId().equals(food.getId()))
        .findFirst();

    if(existing.isPresent()) {
      CartItem existingItem = existing.get();
      int updatedQuantity = existingItem.getQuantity() + request.getQuantity();
      existingItem.setQuantity(updatedQuantity);
      existingItem.setTotalPrice(
          food.getPrice().multiply(BigDecimal.valueOf(updatedQuantity))
      );
    } else {
      CartItem item = new CartItem();
      item.setCart(cart);
      item.setFood(food);
      item.setQuantity(request.getQuantity());
      item.setImageUrl(food.getImages().size() > 0 ? food.getImages().get(0) : null);
      item.setTotalPrice(
          food.getPrice().multiply(new BigDecimal(request.getQuantity()))
      );
      cart.getItems().add(item);
    }

    recalculateCartTotal(cart);
    cartRepository.save(cart);
    return  mapToCartResponse(cart);

  }

  @Override
  public void clearCart() {
    Customer user = authService.getCustomer();
    cartRepository.deleteByCustomerId(user.getId());
  }

  @Override
  public CartResponse updateQuantity(Long cartItemId, Integer quantity) {
    return  null;
  }

  @Override
  public void removeCartItem(Long cartItemId) throws BadRequestException {
    Customer user = authService.getCustomer();

    Cart cart = getCartorThrow();
    CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new BadRequestException("Item not found"));
    cart.getItems().remove(cartItem);
    cartRepository.save(cart);
  }

  @Override
  public Cart getCartByCustomerId(Long userId) {
    return cartRepository.findByCustomerId(userId).orElseThrow(
        () -> new RuntimeException("Cart is not present")
    );
  }


  // -------------------helper method -----------------------------



  private Cart createNewCart() {
    Customer user = authService.getCustomer();

    Cart cart = new Cart();
    cart.setCustomer(user);
    cart.setCartTotal(BigDecimal.ZERO);
    return cartRepository.save(cart);
  }

  private void recalculateCartTotal(Cart cart) {
    BigDecimal total = cart.getItems().stream()
        .map(CartItem::getTotalPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    cart.setCartTotal(total);
  }

  private CartResponse mapToCartResponse(Cart cart) {
    List<CartItemResponse> items = cart.getItems().stream()
        .map(ci-> new CartItemResponse(
            ci.getId(),
            ci.getFood().getId(),
            ci.getFood().getName(),
            ci.getFood().getDescription(),
            ci.getQuantity(),
            ci.getTotalPrice(),
            ci.getFood().getRestaurant().getName(),
            ci.getFood().getImages()
        )).toList();

    return  new CartResponse(
        cart.getId(), items, cart.getCartTotal()
    );

  }

  private Cart getCartOrCreateNew(){
    Customer user = authService.getCustomer();
    return cartRepository.findByCustomerId(
            user.getId())
        .orElseGet(this::createNewCart);
  }

  private Cart getCartorThrow() throws BadRequestException {
    Customer user = authService.getCustomer();
    return cartRepository.findByCustomerId(user.getId()).orElseThrow(
        ()->  new BadRequestException("Cart not found"));
  }
}
