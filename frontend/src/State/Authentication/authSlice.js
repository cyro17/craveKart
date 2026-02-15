import { createSlice } from "@reduxjs/toolkit";
import { loginUser, registerUser } from "./AuthThunks.js";

const initialState = {
    user: {
        id: null,
        username: null,
        email: null,
        roles: []
    },
    token: null,
    isLoading: false,
    error: null,
    success: false,
    isAuthenticated: false
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
        healthCheck(state) {
            state.success = true;
        }
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
                state.token = action.payload.loginData.jwt;
                state.isAuthenticated = true;
                state.success = true;
            })
            .addCase(loginUser.rejected, (state, action) => {
                state.isLoading = false;
                state.error = action.payload;
            })

            // REGISTER
            .addCase(registerUser.pending, (state) => {
                state.isLoading = true;
            })
            .addCase(registerUser.fulfilled, (state) => {
                state.success = true;
                state.isLoading = false;

            }).addCase(registerUser.rejected, (state) => {
                state.isLoading = false;
            });
    },
});

export const { logout, healthCheck } = authSlice.actions;
export const authReducer = authSlice.reducer;
