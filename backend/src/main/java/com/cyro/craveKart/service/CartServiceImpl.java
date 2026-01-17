package com.cyro.cravekart.service;

import com.cyro.cravekart.config.security.AuthService;
import com.cyro.cravekart.models.Cart;
import com.cyro.cravekart.models.CartItem;
import com.cyro.cravekart.models.Food;
import com.cyro.cravekart.repository.CartRepository;
import com.cyro.cravekart.repository.FoodRepository;
import com.cyro.cravekart.request.AddCartItemRequest;
import com.cyro.cravekart.response.CartItemResponse;
import com.cyro.cravekart.response.CartResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

  private final CartRepository cartRepository;
  private final AuthService authService;
  private final FoodRepository foodRepository;

  @Override
  public CartResponse getCart() {
    Cart cart = cartRepository
        .findByCustomerId(authService.getCurrentAuthUser().getId())
        .orElseGet(this::createNewCart);

    return mapToCartResponse(cart);
  }

  @Override
  public CartResponse addItem(AddCartItemRequest request) {
    Cart cart = cartRepository.findByCustomerId(
        authService.getCurrentAuthUser().getId())
        .orElseGet(this::createNewCart);


    Food food = foodRepository.findById(request.getFoodId())
        .orElseThrow(() -> new RuntimeException("Food not found"));

    CartItem item = new CartItem();
    item.setCart(cart);
    item.setFood(food);
    item.setQuantity(request.getQuantity());
    item.setTotalPrice(
        food.getPrice().multiply(new BigDecimal(request.getQuantity()))
    );

    cart.getItems().add(item);
    recalculateCartTotal(cart);
    cartRepository.save(cart);

    return  mapToCartResponse(cart);

  }

  @Override
  public void clearCart() {
    cartRepository.deleteByCustomerId(authService.getCurrentAuthUser().getId());
  }

  @Override
  public CartResponse updateQuantity(Long cartItemId, Integer quantity) {
    return null;
  }

  @Override
  public void removeItem(Long cartItemId) {

  }

  // -------------------helper method -----------------------------

  private Cart createNewCart() {
    Cart cart = new Cart();
    cart.setCustomer(authService.getCurrentAuthUser());
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
            ci.getQuantity(),
            ci.getTotalPrice()
        )).toList();

    return  new CartResponse(
        cart.getId(), items, cart.getCartTotal()
    );

  }
}
