import { createAsyncThunk } from "@reduxjs/toolkit";
import api from "../../config/api";

export const placeOrder = createAsyncThunk(
    "order/placeOrder",
    async (_, { rejectWithValue }) => {
        try {
            const { data } = await api.post("/customer/order/place");
            console.log(data);
            return data;
        } catch (err) {
            return rejectWithValue(err.response?.data || err.message);
        }
    }
)

export const fetchOrders = createAsyncThunk(
    "order/fetch",
    async (_, { rejectWithValue }) => {
        try {
            const { data } = await api.get("/customer/order");
            console.log(data);
            return data;
        } catch (err) {
            return rejectWithValue(err.response?.data || err.message);
        }
    }
)