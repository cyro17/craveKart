package com.cyro.craveKart.service;

import com.cyro.craveKart.exceptions.CartException;
import com.cyro.craveKart.exceptions.CartItemException;
import com.cyro.craveKart.exceptions.FoodException;
import com.cyro.craveKart.exceptions.UserException;
import com.cyro.craveKart.model.Cart;
import com.cyro.craveKart.model.CartItem;
import com.cyro.craveKart.request.AddCartItemRequest;

public interface CartSerive {

	public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws UserException, FoodException, CartException, CartItemException;

	public CartItem updateCartItemQuantity(Long cartItemId,int quantity) throws CartItemException;

	public Cart removeItemFromCart(Long cartItemId, String jwt) throws UserException, CartException, CartItemException;

	public Long calculateCartTotals(Cart cart) throws UserException;
	
	public Cart findCartById(Long id) throws CartException;
	
	public Cart findCartByUserId(Long userId) throws CartException, UserException;
	
	public Cart clearCart(Long userId) throws CartException, UserException;
	

	

}
