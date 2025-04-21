package com.cyro.craveKart.controller;

import com.cyro.craveKart.exception.CartException;
import com.cyro.craveKart.exception.CartItemException;
import com.cyro.craveKart.exception.UserException;
import com.cyro.craveKart.model.Cart;
import com.cyro.craveKart.model.CartItem;
import com.cyro.craveKart.model.User;
import com.cyro.craveKart.request.AddCartItemRequest;
import com.cyro.craveKart.request.UpdateCartItemRequest;
import com.cyro.craveKart.service.CartService;
import com.cyro.craveKart.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserServiceImpl userService;

    @PutMapping("/cart-item/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemRequest request,
                                                  @RequestHeader("Authorization") String jwt)
            throws CartItemException, CartException, UserException {
        CartItem cartItem = cartService.addItemToCart(request, jwt);
        return ResponseEntity.ok(cartItem);
    }

    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItemQty(@RequestBody UpdateCartItemRequest request,
                                                  @RequestHeader("Authorization") String jwt)
            throws CartItemException {
        CartItem cartItem = cartService.updateCartItemQuantity(request.getCartItemId(), request.getQuantity());
        return ResponseEntity.ok(cartItem);
    }

    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart> removeItemFromCart(@PathVariable Long id,
                                                   @RequestHeader("Authorization") String jwt)
            throws UserException, CartException, CartItemException {

        Cart cart = cartService.removeItemFromCart(id, jwt);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/cart/total")
    public ResponseEntity<Double> calculateCartTotals(@RequestParam Long cartId,
                                                      @RequestHeader("Authorization") String jwt)
            throws UserException, CartException {
        User user = userService.findUserProfileByJwtToken(jwt);
        Cart cart = cartService.findCartByUserId(user.getId());
        double total = cartService.calculateCartTotals(cart);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCart(
            @RequestHeader("Authorization") String jwt) throws UserException, CartException {
        User user=userService.findUserProfileByJwtToken(jwt);
        Cart cart = cartService.findCartByUserId(user.getId());
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> clearCart(
            @RequestHeader("Authorization") String jwt) throws UserException, CartException {
        User user=userService.findUserProfileByJwtToken(jwt);
        Cart cart = cartService.clearCart(user.getId());
        return ResponseEntity.ok(cart);
    }
}
