import { createSlice } from "@reduxjs/toolkit";
import { fetchRestaurants } from "./RestaurantThunks";

const initialState = {
    list: [],
    favorites: [],
    loading: false,
    error: null,
};



const restaurantSlice = createSlice({
    name: "restaurants",
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
        builder
            .addCase(fetchRestaurants.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(fetchRestaurants.fulfilled, (state, action) => {
                state.loading = false;
                state.list = action.payload;

            })
            .addCase(fetchRestaurants.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload;
            });
    }
})

export const { addFavorite, removeFavorite } = restaurantSlice.actions;
export const restaurantReducer = restaurantSlice.reducer;