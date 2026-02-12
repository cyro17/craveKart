import { createAsyncThunk } from "@reduxjs/toolkit";
import api from "../../config/api";


export const fetchRestaurants = createAsyncThunk(
    "restaurant/fetchRestaurants",
    async (_, { rejectWithValue }) => {

        try {
            const res = await api.get("/customer/restaurants");
            console.log("restaurant data: ", res.data);
            return res.data;
        } catch (err) {
            console.error("fetchRestaurants error:", err.response || err);
            if (err.response?.data?.message)
                return rejectWithValue(err.response.data.message);
            return rejectWithValue(err.message || "Failed to fetch restaurants");
        }
    }
)

export const fetchRestaurantsByCity = createAsyncThunk(
    "restaurant/fetchRestaurantsByCity",
    async (filters = {}, { rejectWithValue }) => {
        try {
            const { city, cuisine, rating, sort } = filters;
            const params = new URLSearchParams();

            if (city) params.append("city", city);
            if (cuisine) params.append("cuisine", cuisine);
            if (rating) params.append("rating", rating);
            if (sort) params.append("sort", sort);

            const res = await api.get(
                `/customer/restaurants?${params.toString}`
            );
            return res.data;
        } catch (err) {
            return rejectWithValue(
                err.response?.data?.message || err.message || "Failed to fetch restaurants"
            );
        }
    }
)