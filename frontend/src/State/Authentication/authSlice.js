import { createSlice } from "@reduxjs/toolkit";
import { loginUser, registerUser } from "./AuthThunks.js";

const initialState = {
    user: null,
    token: null,
    isLoading: false,
    error: null,
    success: false,
};

const authSlice = createSlice({
    name: "auth",
    initialState,
    reducers: {
        logout(state) {
            state.user = null;
            state.token = null;
            state.success = false;
            localStorage.removeItem("token");
        },
    },
    extraReducers: (builder) => {
        builder
            // LOGIN
            .addCase(loginUser.pending, (state) => {
                state.isLoading = true;
            })
            .addCase(loginUser.fulfilled, (state, action) => {
                state.isLoading = false;
                state.user = action.payload.user;
                state.token = action.payload.token;
                state.success = true;
            })
            .addCase(loginUser.rejected, (state, action) => {
                state.isLoading = false;
                state.error = action.payload;
            })

            // REGISTER
            .addCase(registerUser.fulfilled, (state) => {
                state.success = true;
            });
    },
});

export const { logout } = authSlice.actions;
export const authReducer = authSlice.reducer;
