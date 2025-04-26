import { createSlice } from "@reduxjs/toolkit";
import { reducers } from "./reducers";

const initialState = {
    loading: false,
    orders: [],
    error: null,
    notifications: []
};

const orderSlice = createSlice({
    name: "order",
    initialState,
    reducers: reducers
})

export const { getUsersOrdersRequest, getUsersOrdersSuccess,
    getUsersNotificationSuccess, getUsersOrdersFailure
} = orderSlice.actions;

export const orderReducer = orderSlice.reducer;
