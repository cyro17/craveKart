import { createAsyncThunk } from "@reduxjs/toolkit";
import api from "../../config/api";


export const fetchRestaurants = createAsyncThunk(
    "restaurants/fetchRestaurants",
    async (_, { rejectWithValue }) => {

        try {
            const res = await api.get("/customer/restaurants");
            console.log("restaurant data: ", res.data);
            return res.data;
        } catch (err) {
            console.error("fetchRestaurants error:", err.response || err);
            if (err.response?.data?.message) return rejectWithValue(err.response.data.message);
            return rejectWithValue(err.message || "Failed to fetch restaurants");
        }
    }
)