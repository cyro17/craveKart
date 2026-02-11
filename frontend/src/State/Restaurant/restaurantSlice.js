import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { api } from "../../component/config/api";

const initialState = {
    list: [],
    favorites: [],
    loading: false,
    error: null,
};

export const fetchRestaurants = createAsyncThunk(
    "restaurants/fetchRestaurants",
    async (_, { rejectWithValue }) => {
        try {
            const res = await api.get("/customer/restaurants");
            return res.data;
        } catch (err) {
            return rejectWithValue(err.message);
        }
    }
)

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