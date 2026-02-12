import { createSlice } from "@reduxjs/toolkit";
import { fetchRestaurants, fetchRestaurantsByCity } from "./RestaurantThunks";

const initialState = {
    list: [],
    favorites: [],
    loading: false,
    error: null,
};



const restaurantSlice = createSlice({
    name: "restaurant",
    initialState,
    reducers: {
        addFavorite: (state, action) => {
            if (!state.favorites.includes(action.payload)) {
                state.favorites.push(action.payload);
            }
        },
        removeFavorite: (state, action) => {
            state.favorites = state.favorites.filter(id => id !== action.payload);
        },
    },
    extraReducers: builder => {
        const handlePending = (state) => {
            state.loading = true;
            state.error = null;
        };

        const handleRejected = (state, action) => {
            state.loading = false;
            state.error = action.payload || "Something went wrong";
        };
        builder
            .addCase(fetchRestaurants.pending, handlePending)

            .addCase(fetchRestaurants.fulfilled, (state, action) => {
                state.loading = false;
                state.list = action.payload;

            })
            .addCase(fetchRestaurants.rejected, handleRejected)

            .addCase(fetchRestaurantsByCity.pending, handlePending)

            .addCase(fetchRestaurantsByCity.fulfilled, (state, action) => {
                state.loading = false;
                state.list = Array.isArray(action.payload) ?
                    action.payload :
                    action.payload.restaurants || [];
            })

            .addCase(fetchRestaurantsByCity.rejected, handleRejected);
    }
})

export const { addFavorite, removeFavorite } = restaurantSlice.actions;
export const restaurantReducer = restaurantSlice.reducer;