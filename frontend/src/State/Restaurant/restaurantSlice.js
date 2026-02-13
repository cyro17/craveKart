import { createSlice } from "@reduxjs/toolkit";
import { fetchRestaurantById, fetchRestaurantMenu, fetchRestaurants, fetchRestaurantsFilter } from "./RestaurantThunks";

const initialState = {
    list: [],
    filtered: [],
    selectedRestaurant: null,
    favorites: [],
    menu: {},
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
            // fetch all restaurants
            .addCase(fetchRestaurants.pending, handlePending)
            .addCase(fetchRestaurants.rejected, handleRejected)
            .addCase(fetchRestaurants.fulfilled, (state, action) => {
                state.loading = false;
                state.list = action.payload;
                state.filtered = action.payload;

            })

            // filtered restaurants
            .addCase(fetchRestaurantsFilter.pending, handlePending)
            .addCase(fetchRestaurantsFilter.rejected, handleRejected)
            .addCase(fetchRestaurantsFilter.fulfilled, (state, action) => {
                state.loading = false;
                state.filtered = Array.isArray(action.payload) ?
                    action.payload :
                    action.payload.restaurants || [];
            })

            // fetch restaurant by id
            .addCase(fetchRestaurantById.pending, handlePending)
            .addCase(fetchRestaurantById.rejected, handleRejected)
            .addCase(fetchRestaurantById.fulfilled, (state, action) => {
                console.log("REDUX GOT:", action.payload);
                state.loading = false;
                state.selectedRestaurant = action.payload;
            })

            // fetch restaurant menu
            .addCase(fetchRestaurantMenu.pending, handlePending)
            .addCase(fetchRestaurantMenu.rejected, handleRejected)
            .addCase(fetchRestaurantMenu.fulfilled, (state, action) => {
                state.loading = false;
                state.menu = action.payload;
            })

    }
})

export const { addFavorite, removeFavorite } = restaurantSlice.actions;
export const restaurantReducer = restaurantSlice.reducer;