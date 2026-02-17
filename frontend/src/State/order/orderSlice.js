import { createSlice } from "@reduxjs/toolkit";
import { fetchOrders, placeOrder } from "./orderThunk";

const initialState = {
    list: [],
    loading: false,
    error: null,
    orderId: null,
    totalPrice: 0,
    success: false
}

const handlePending = (state, action) => {
    state.loading = true;
    state.error = null;
    state.success = false;
}

const handleRejected = (state, action) => {
    state.loading = false;
    state.error = action.payload || "Failed to place order";
}

const orderSlice = createSlice({
    name: "order",
    initialState,
    reducers: {
        resetOrder: (state) => {
            state.loading = false;
            state.error = null;
            state.orderId = null;
            state.success = null;
            state.totalPrice = 0;
        },
    },
    extraReducers: (builder) => {
        builder
            // place order
            .addCase(placeOrder.pending, handlePending)
            .addCase(placeOrder.rejected, handleRejected)
            .addCase(placeOrder.fulfilled, (state, action) => {
                state.loading = false;
                state.orderId = action.payload?.orderId;
                state.totalPrice = action.payload?.totalPrice;
                state.success = true;
            })

            // fetch order
            .addCase(fetchOrders.pending, handlePending)
            .addCase(fetchOrders.rejected, handleRejected)
            .addCase(fetchOrders.fulfilled, (state, action) => {
                state.loading = false;
                state.list = action.payload;
                state.success = true;
            })
    }
});

export const { resetOrder } = orderSlice.actions;
export const orderReducer = orderSlice.reducer;