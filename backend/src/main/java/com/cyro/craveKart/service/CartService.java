package com.cyro.craveKart.service;

import com.cyro.craveKart.exception.CartException;
import com.cyro.craveKart.exception.CartItemException;
import com.cyro.craveKart.exception.FoodException;
import com.cyro.craveKart.exception.UserException;
import com.cyro.craveKart.model.Cart;
import com.cyro.craveKart.model.CartItem;
import com.cyro.craveKart.request.AddCartItemRequest;
import org.bson.types.ObjectId;

public interface CartService {

    public CartItem addItemToCart(AddCartItemRequest request, String jwt)
            throws UserException, FoodException, CartException, CartItemException;

    public CartItem updateCartItemQuantity(ObjectId cartItemId,int quantity) throws CartItemException;

    public Cart removeItemFromCart(ObjectId cartItemId, String jwt) throws UserException, CartException, CartItemException;

    public Long calculateCartTotals(Cart cart) throws UserException;

    public Cart findCartById(ObjectId id) throws CartException;

    public Cart findCartByUserId(ObjectId userId) throws CartException, UserException;

    public Cart clearCart(ObjectId userId) throws CartException, UserException;

}
