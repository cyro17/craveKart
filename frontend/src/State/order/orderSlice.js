import { createSlice } from "@reduxjs/toolkit";
import { fetchOrders, placeOrder } from "./orderThunk";

const initialState = {
    orders: [],
    selectedOrder: null,

    loading: false,
    error: null,
    pagination: {
        page: 0,
        size: 10,
        totalPages: 0,
        totalElements: 0
    },
    filters: {
        status: null,
        dateRange: null
    },

    // payment 
    orderId: null,
    totalPrice: 0,
    success: false,
    clientSecret: null,
    paymentStep: "idle",  // idle -> placing-> pay-> confirmed/failed -> idle
    paymentMethod: null,

    idempotencyKey: null


}

const handlePending = (state, action) => {
    state.loading = true;
    state.error = null;
    state.success = false;

}

const handleRejected = (state, action) => {
    state.loading = false;
    state.error = action.payload || "Failed to place order";
    state.paymentStep = "failed";
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
            state.clientSecret = null;
            state.paymentStep = "idle";
            state.idempotencyKey = null;
        },

        setPaymentConfirmed: (state) => {
            state.paymentStep = "confirmed";
            state.clientSecret = null;
            state.success = true;
        },

        setPaymentFailed: (state, action) => {
            state.paymentStep = "failed";
            state.error = action.payload || "Payment failed.";
        },
        setIdempotencyKey: (state, action) => {
            state.idempotencyKey = action.payload;
        }
    },
    extraReducers: (builder) => {
        builder
            // place order
            .addCase(placeOrder.pending, (state) => {
                state.loading = true;
                state.error = null;
                state.success = false;
                state.paymentStep = "placing";
            })
            .addCase(placeOrder.rejected, handleRejected)
            .addCase(placeOrder.fulfilled, (state, action) => {
                console.log("order place response: ", action.payload);
                state.loading = false;
                state.success = true;
                state.orderId = action.payload?.orderId;
                state.totalPrice = action.payload?.pricing?.total;
                state.clientSecret = action.payload?.clientSecret;
                state.paymentStep = "pay";
            })

            // fetch order
            .addCase(fetchOrders.pending, handlePending)
            .addCase(fetchOrders.rejected, handleRejected)
            .addCase(fetchOrders.fulfilled, (state, action) => {
                console.log(action.payload);
                state.loading = false;
                state.orders = action.payload;
                state.success = true;
            })
    }
});

export const { resetOrder, setPaymentConfirmed, setPaymentFailed, setIdempotencyKey } = orderSlice.actions;
export const orderReducer = orderSlice.reducer;