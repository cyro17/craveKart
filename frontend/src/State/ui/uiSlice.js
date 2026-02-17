import { createSlice } from "@reduxjs/toolkit";

const uiSlice = createSlice({
    name: "ui",
    initialState: {
        cartOpen: false,
        authOpen: false,
        authMode: "login",
        redirectAfterLogin: "/"
    },
    reducers: {
        openCart: (state) => {
            state.cartOpen = true;
        },
        closeCart: (state) => {
            state.cartOpen = false;
        },
        openAuth: (state, action) => {
            state.authOpen = true;
            state.redirectAfterLogin = action.payload?.redirectAfterLogin || "/";
            state.authMode = action.payload?.authMode || "login"
        },
        closeAuth: (state) => {
            state.authOpen = false;
        },
        setAuthMode: (state, action) => {
            state.authMode = action.payload;
        }
    }
});

export const { openCart, closeCart, openAuth, closeAuth, setAuthMode } = uiSlice.actions;
export const uiReducer = uiSlice.reducer; 