import { createAsyncThunk } from "@reduxjs/toolkit";
import api from "../../config/api";


export const fetchAddress = createAsyncThunk(
    "address/fetch",
    async (_, { rejectWithValue }) => {
        try {
            const res = await api.get("/customer/address");
            return res.data;
        } catch (err) {
            return rejectWithValue(err.response?.data);
        }
    }
)

export const postAddress = createAsyncThunk(
    "address/add",
    async (values, { rejectWithValue }) => {
        console.log("request intercepted values : ", values);
        try {
            const res = await api.post("/customer/address", values);
            return res.data;

        } catch (error) {
            return rejectWithValue(error.response.data.message);
        }
    }
)