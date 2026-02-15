import { createSlice } from "@reduxjs/toolkit";

const uiSlice = createSlice({
    name: "ui",
    initialState: {
        cartOpen: false,
        authOpen: false,
        authMode: "login"
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
            state.authMode = action.payload || "login"
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