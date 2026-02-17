import { createAsyncThunk } from "@reduxjs/toolkit";
import api from "../../config/api";


export const fetchCart = createAsyncThunk(
    "cart/fetchCart",
    async (_, { rejectWithValue }) => {
        try {
            const response = await api.get(`/customer/cart`);
            // console.log("cart response : ", response.data);
            return response.data;

        } catch (err) {
            return rejectWithValue(
                err.response?.data || "Failed to fetch cart items"
            );
        }
    }
)

export const addToCart = createAsyncThunk(
    "cart/add",
    async (id, { rejectWithValue }) => {
        try {
            const res = await api.post(`/customer/cart/add/${id}`);
            console.log("add food to cart response: ", res.data);
            return res.data;
        } catch (err) {
            return rejectWithValue(
                err.response?.data || "Failed to fetch cart items"
            );
        }
    }
)

export const incrementCartItem = createAsyncThunk(
    "cart/inc",
    async (id, { rejectWithValue }) => {
        try {
            const res = await api.put(`/customer/cart/cartItem/inc/${id}`);
            return res.data;

        } catch (err) {
            return rejectWithValue(
                err.response?.data || "Failed to increment cart item"
            )
        }
    }
)


export const decrementCartItem = createAsyncThunk(
    "cart/dec",
    async (id, { rejectWithValue }) => {
        try {
            const res = await api.put(`/customer/cart/cartItem/dec/${id}`);
            return res.data;
        } catch (err) {
            return rejectWithValue(
                err.response?.data || "Failed to increment cart item"
            )
        }
    }
)

export const removeCartItem = createAsyncThunk(
    "cart/removeCartItem",
    async (id, { rejectWithValue }) => {
        try {
            const res = await api.delete(`/customer/cart/cartItem/${id}`);
            return res.data;
        } catch (err) {
            return rejectWithValue(
                err.response?.data?.message || "Failed to remove item"
            );
        }

    }
)

export const clearCart = createAsyncThunk(
    "cart/clear",
    async (_, { rejectWithValue }) => {
        try {
            await api.delete(`/customer/cart/`);
        } catch (err) {
            return rejectWithValue(
                err.response?.data || "Failed to clear cart."
            )
        }
    }
)

