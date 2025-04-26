import { createSlice } from "@reduxjs/toolkit";
import { reducers } from "./reducers";

const initialState = {
    cart: null,
    cartItems: [],
    loading: false,
    error: null
}

const cartSlice = createSlice({
    name: "cart",
    initialState,
    reducers: reducers,
})

export const {
    findCartRequest, getAllCartItemsRequest,
    updateCartItemRequest, removeCartItemrequest, findCartSuccess,
    clearCartSuccess, addItemToCartSuccess, updateCartItemFailure, updateCartItemSuccess,
    findCartFailure, removeCartItemFailure, removeCartItemSuccess, logout,
} = cartSlice.actions;

export const cartReducer = cartSlice.reducer;