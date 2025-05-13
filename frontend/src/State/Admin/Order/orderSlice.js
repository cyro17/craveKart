import { createSlice } from "@reduxjs/toolkit";
import { reducers } from "./reducers";


const initialState = {
    loading: false,
    error: null,
    orders: []
};

const orderSlice = createSlice({
    name: "admin/order",
    initialState,
    reducers: reducers
});

export const {
    getRestaurantsOrderRequest, updateOrderStatusRequest,
    getRestaurantsOrderSuccess, updateOrderStatusSuccess,
    getRestaurantsOrderFailure, updateOrderStatusFailure
} = orderSlice.actions;

export const restaurantsOrderReducer = orderSlice.reducer;
