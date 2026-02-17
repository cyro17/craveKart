import { createSlice } from "@reduxjs/toolkit";
import { fetchAddress, postAddress } from "./addressThunk";

const initialState = {
    addresses: [],
    selectedAddress: null,
    loading: false,
};
const handlePending = (state) => {
    state.loading = true;
    state.error = null;
};

const handleRejected = (state, action) => {
    state.loading = false;
    state.error = action.payload || "Something went wrong";
};

const addressSlice = createSlice({
    name: "address",
    initialState,
    reducers: {
        setSelectedAddress: (state, action) => {
            state.selectedAddress = action.payload;
        },
    },
    extraReducers: (builder) => {
        builder
            // fetch address
            .addCase(fetchAddress.pending, handlePending)
            .addCase(fetchAddress.rejected, handleRejected)
            .addCase(fetchAddress.fulfilled, (state, action) => {
                console.log(action.payload);
                state.loading = false;
                state.addresses = action.payload;
                if (!state.selectedAddress && action.payload.length > 0) {
                    state.selectedAddress = action.payload[0];
                }
            })

            // post address
            .addCase(postAddress.pending, handlePending)
            .addCase(postAddress.rejected, handleRejected)
            .addCase(postAddress.fulfilled, (state, action) => {
                state.addresses.unshift(action.payload);
                state.loading = false;
            })


    },
});

export const { setSelectedAddress } = addressSlice.actions;
export const addressReducer = addressSlice.reducer;


