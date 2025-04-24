import { createSlice } from "@reduxjs/toolkit";

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
    reducers: {
        createRestaurantRequest: (state) => {
            state.loading = true;
            state.error = null;
        },
        getAllRestaurantRequest: (state) => {
            state.loading = true;
            state.error = null;
        },
        deleteRestaurantRequest: (state) => {
            state.loading = true;
            state.error = null;
        },
        updateRestaurantRequest: (state) => {
            state.loading = true;
            state.error = null;
        },
        getRestaurantByIdRequest: (state) => {
            state.loading = true;
            state.error = null;
        },
        createCategoryRequest: (state) => {
            state.loading = true;
            state.error = null;
        },
        getRestaurantCategoryRequest: (state) => {
            state.loading = true;
            state.error = null;
        },
        createRestaurantSuccess: (state, action) => {
            state.loading = false;
            state.usersRestaurant = action.payload;
        },
        getAllRestaurantSuccess: (state, action) => {
            state.loading = false;
            state.restaurants = action.payload;
        },
        getRestaurantByIdSuccess: (state, action) => {
            state.loading = false;
            state.restaurant = action.payload;
        },
        getRestaurantByUserIdSuccess: (state, action) => {
            state.loading = false;
            state.usersRestaurant = action.payload;
        },
        updateRestaurantStatusSuccess: (state, action) => {
            state.loading = false;
            state.restaurant = action.payload;
        },
        updateRestaurantSuccess: (state, action) => {
            state.loading = false;
            state.restaurant = action.payload;
        },
        deleteRestaurantSuccess: (state, action) => {
            state.error = null;
            state.loading = false;
            state.restaurants = state.restaurants.filter(
                (restaurant) => restaurant.id !== action.payload
            );
            state.usersRestaurant = state.usersRestaurant.filter(
                (restaurant) => restaurant.id !== action.payload
            );
        },
        createEventsSuccess: (state, action) => {
            state.loading = false;
            state.events = [...state.events, action.payload];
            state.restaurantsEvents = [...state.restaurantsEvents, action.payload];
        },
        getRestaurantsEventsSuccess: (state, action) => {
            state.loading = false;
            state.restaurantsEvents = action.payload;
        },


    }
});

export const { createRestaurantRequest, getAllRestaurantRequest,
    deleteRestaurantRequest, updateRestaurantRequest, getRestaurantByIdRequest,
    createCategoryRequest, getRestaurantCategoryRequest,
    createRestaurantSuccess, getAllRestaurantSuccess, getRestaurantByIdSuccess,
    getRestaurantByUserIdSuccess, updateRestaurantStatusSuccess,
    updateRestaurantSuccess, deleteRestaurantSuccess,
    createEventsSuccess, getRestaurantsEventsSuccess
} = restaurantSlice.actions;

export const restaurantReducer = restaurantSlice.reducer;