import { createSlice } from "@reduxjs/toolkit";
import { reducers } from "./reducers";


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
    reducers: reducers
})

export const { registerRequest, loginRequest, getUserRequest, resetPasswordRequest, requestResetPasswordRequest,
    registerSuccess, loginSuccess, logout } = authSlice.actions;

export const authReducer = authSlice.reducer;

