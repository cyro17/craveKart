import { createSlice } from "@reduxjs/toolkit";
import { reducers } from "./reducers";
import { getAllRestaurant } from "./RestaurantThunks";

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
        resetError: (state) => {
            state.error = null;
        }
    },
    extraReducers: (builder) => {
        builder
            .addCase(getAllRestaurant.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(getAllRestaurant.fulfilled, (state, action) => {
                state.loading = false;
                state.restaurants = action.payload;
            })
            .addCase(getAllRestaurant.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload;
            })
    }
});

// export const { createRestaurantRequest, getAllRestaurantRequest,
//     deleteRestaurantRequest, updateRestaurantRequest, getRestaurantByIdRequest,
//     createCategoryRequest, getRestaurantCategoryRequest,
//     createRestaurantSuccess, getAllRestaurantSuccess, getRestaurantByIdSuccess,
//     getRestaurantByUserIdSuccess, updateRestaurantStatusSuccess,
//     updateRestaurantSuccess, deleteRestaurantSuccess,
//     createEventsSuccess, getRestaurantsEventsSuccess, createRestaurantFailure, getAllRestaurantFailure,
//     deleteEventSuccess, updateRestaurantFailure, getRestaurantCategoryFailure, createCategoryFailure,
//     createCategorySuccess, getRestaurantCategorySuccess, getRetaurantByIdFailure
// } = restaurantSlice.actions;

export const { resetError } = restaurantSlice.actions;

export const restaurantReducer = restaurantSlice.reducer;