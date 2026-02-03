import { createSlice } from "@reduxjs/toolkit";
// import { reducers } from "./reducers";
import { loginUser } from "./AuthThunks";
import { registerUser } from "./AuthThunks";


const initialState = {
    user: null,
    isLoading: false,
    error: null,
    jwt: null,
    favorites: [],
    success: null,
}

const authSlice = createSlice({
    name: "auth",
    initialState,
    reducers: {
        logout: (state) => {
            localStorage.removeItem("jwt");
            state.user = null;
            state.jwt = null;
            state.success = "Logout Successful";
        },
        clearAuthState: (state) => {
            state.error = null;
            state.success = null;
        },
    },
    extraReducers: (builder) => {
        builder
            .addCase(loginUser.pending, (state) => {
                state.isLoading = true;
                state.error = null;
                state.success = null;

            })
            .addCase(loginUser.fulfilled, (state, action) => {
                state.isLoading = false;
                state.jwt = action.payload?.jwt;
                state.success = "Login Success";
            })
            .addCase(loginUser.rejected, (state, action) => {
                state.isLoading = false;
                state.error = action.payload;
            })
            .addCase(registerUser.pending, (state) => {
                state.isLoading = true;
                state.error = null;
                state.success = null;
            })
            .addCase(registerUser.fulfilled, (state, action) => {
                state.isLoading = false;
                state.success = "Register Success";
            })
            .addCase(registerUser.rejected, (state, action) => {
                state.isLoading = false;
                state.error = action.payload;
            })
    }
});

// export const { registerRequest,
//     loginRequest, getUserRequest, resetPasswordRequest, requestResetPasswordRequest,
//     registerSuccess, loginSuccess, logout } = authSlice.actions;

export const { logout, clearAuthState } = authSlice.actions;

export const authReducer = authSlice.reducer;

