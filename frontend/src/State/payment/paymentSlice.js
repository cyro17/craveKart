import { createSlice } from "@reduxjs/toolkit";
import { createCheckoutSession } from "./paymentThunk";

const initialState = {
    loading: false,
    error: null,
    sessionUrl: ""
}
const handlePending = (state) => {
    state.loading = true;
    state.error = null;
};

const handleRejected = (state, action) => {
    state.loading = false;
    state.error = action.payload || "Something went wrong";
};

const paymentSlice = createSlice({
    name: "",
    initialState,
    reducers: {
        resetPayment: (state) => {
            state.loading = false;
            state.error = null;
            state.sessionUrl = ""
        }
    }, extraReducers: (builder) => {
        builder
            .addCase(createCheckoutSession.pending, handlePending)
            .addCase(createCheckoutSession.rejected, handleRejected)
            .addCase(createCheckoutSession.fulfilled, (state, action) => {
                console.log(action.payload);
                state.loading = false;
                state.sessionUrl = action.payload?.sessionUrl;
            })
    }
});

export const { resetPayment } = paymentSlice.actions;
export const paymentReducer = paymentSlice.reducer;
