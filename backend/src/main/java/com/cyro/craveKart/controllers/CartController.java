package com.cyro.cravekart.controllers;

import com.cyro.cravekart.repository.CartRepository;
import com.cyro.cravekart.request.AddCartItemRequest;
import com.cyro.cravekart.response.CartResponse;
import com.cyro.cravekart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;

  @GetMapping("/check")
  public ResponseEntity<String> health(){
    return  new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<CartResponse> getCart() {
    return  new  ResponseEntity<>(cartService.getCart(), HttpStatus.OK);
  }

  @PostMapping("/add")
  public CartResponse addToCart(@RequestBody AddCartItemRequest request){
    return  cartService.addItem(request);
  }

  @PutMapping("/item/{cartItemId}")
  public CartResponse updateQuantity(
      @PathVariable Long cartItemId,
      @RequestParam Integer quantity
  ){
    return  cartService.updateQuantity(cartItemId, quantity);
  }

  @DeleteMapping("/item/{cartItemId}")
  public void removeItem(@PathVariable Long cartItemId){
    cartService.removeItem(cartItemId);
  }

  @DeleteMapping("/clear")
  public void clearCart(){
    cartService.clearCart();
  }

}
