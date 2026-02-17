import { createSlice } from "@reduxjs/toolkit";
import { addToCart, clearCart, decrementCartItem, fetchCart, incrementCartItem, removeCartItem } from "./cartThunk";
import { act } from "react";

const initialState = {
    cartId: null,
    items: [],
    cartTotal: 0,
    discount: 0,
    deliveryCharges: 0,
    tax: 0,
    isDrawerOpen: false,
    loading: false,
    loadingItemId: null,
    error: null
}

const handlePending = (state) => {
    state.loading = true;
    state.error = null;
};

const handleRejected = (state, action) => {
    state.loading = false;
    state.error = action.payload || "Something went wrong";
};

const setCartState = (state, action) => {
    state.loading = false;
    state.cartId = action.payload.cartId;
    state.items = action.payload.items;
    state.cartTotal = action.payload.cartTotal;
    state.discount = action.payload.discount ?? 0;
    state.deliveryCharges = action.payload.deliveryCharges ?? 0;
    state.tax = action.payload.tax ?? 0;
}

const cartSlice = createSlice({
    name: "cart",
    initialState,
    reducers: {

        openDrawer: (state) => {
            state.isDrawerOpen = true;
        },

        closeDrawer: (state) => {
            state.isDrawerOpen = false;
        }
    },
    extraReducers: (builder) => {
        builder
            // Fetch cart 
            .addCase(fetchCart.pending, handlePending)
            .addCase(fetchCart.rejected, handleRejected)
            .addCase(fetchCart.fulfilled, (state, action) => {
                // console.log(action.payload);
                state.loading = false;
                state.items = action.payload.items;
                state.cartTotal = action.payload.cartTotal;
            })

            // add to cart
            .addCase(addToCart.pending, handlePending)
            .addCase(addToCart.rejected, handleRejected)
            .addCase(addToCart.fulfilled, setCartState)


            // increment cart item
            .addCase(incrementCartItem.pending, handlePending)
            .addCase(incrementCartItem.rejected, handleRejected)
            .addCase(incrementCartItem.fulfilled, setCartState)

            // decrement cart item
            .addCase(decrementCartItem.pending, handlePending)
            .addCase(decrementCartItem.rejected, handleRejected)
            .addCase(decrementCartItem.fulfilled, setCartState)

            // remove cart item
            .addCase(removeCartItem.pending, (state, action) => {
                state.loadingItemId = action.meta.arg;
            })
            .addCase(removeCartItem.rejected, (state, action) => {
                state.loadingItemId = null;
                state.error = action.payload || "Something went wrong";
            })
            .addCase(removeCartItem.fulfilled, setCartState)

            // clear cart
            .addCase(clearCart.pending, handlePending)
            .addCase(clearCart.rejected, handleRejected)
            .addCase(clearCart.fulfilled, (state) => {
                state.loading = false;
                state.cartId = null;
                state.items = [];
                state.cartTotal = 0;
                state.discount = 0;
                state.deliveryCharges = 0;
                state.tax = 0;
            })


    }
})

export const { openDrawer, closeDrawer } = cartSlice.actions;

export const cartReducer = cartSlice.reducer;