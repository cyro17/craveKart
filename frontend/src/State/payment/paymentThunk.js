import { createAsyncThunk } from "@reduxjs/toolkit";
import api from "../../config/api";

export const createCheckoutSession = createAsyncThunk(
    "payment/checkout",
    async (values, { rejectWithValue }) => {
        try {
            const res = await api.post("/payment/checkout", values);
            console.log(res.data);
            return res.data;
        } catch (err) {
            const message = err.response?.data?.message ||
                "Login failed. Please try again.";
            return rejectWithValue(message);
        }
    }
)