import { createSlice } from "@reduxjs/toolkit";
import { reducers } from "./reducers";

const initialState = {
    menuItems: [],
    loading: false,
    error: null,
    search: [],
    message: null
}

const menuSlice = createSlice({
    name: "menu",
    initialState,
    reducers: reducers
});

export const {
    createMenuItemRequest, getMenuItemsByRestaurantIdRequest,
    deleteMenuItemRequest, searchMenuItemRequest, updateMenuItemsAvailabilityRequest,
    createMenuItemSuccess, getMenuItemsByRestaurantIdSuccess, deleteMenuItemSuccess,
    updateMenuItemsAvailabiliySuccess, searchMenuItemFailure, searchMenuItemSuccess,
    createMenuItemFailure, getMenuItemsByRestaurantIdFailure, deleteMenuItemFailure,
    updateMenuItemsAvailabilityFailure
} = menuSlice.actions;

export const menuReducer = menuSlice.reducer;