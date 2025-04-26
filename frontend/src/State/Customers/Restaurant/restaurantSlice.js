import { createSlice } from "@reduxjs/toolkit";
import { reducers } from "./reducers";

const initialState = {
    restaurants: [],
    usersRestaurant: null,
    restaurant: null,
    loading: false,
    error: null,
    events: [],
    restaurantsEvents: [],
    categories: [],
}

const restaurantSlice = createSlice({
    name: "restaurant",
    initialState,
    reducers: reducers
});

export const { createRestaurantRequest, getAllRestaurantRequest,
    deleteRestaurantRequest, updateRestaurantRequest, getRestaurantByIdRequest,
    createCategoryRequest, getRestaurantCategoryRequest,
    createRestaurantSuccess, getAllRestaurantSuccess, getRestaurantByIdSuccess,
    getRestaurantByUserIdSuccess, updateRestaurantStatusSuccess,
    updateRestaurantSuccess, deleteRestaurantSuccess,
    createEventsSuccess, getRestaurantsEventsSuccess, createRestaurantFailure, getAllRestaurantFailure,
    deleteEventSuccess, updateRestaurantFailure, getRestaurantCategoryFailure, createCategoryFailure,
    createCategorySuccess, getRestaurantCategorySuccess, getRetaurantByIdFailure
} = restaurantSlice.actions;

export const restaurantReducer = restaurantSlice.reducer;