import { createAsyncThunk } from "@reduxjs/toolkit";
import api from "../../config/api";

export const loginUser = createAsyncThunk(
    "auth/login",
    async (values, { rejectWithValue }) => {
        try {
            const { data: loginData } = await api.post("/auth/signin", values);
            // console.log("login response data: ", loginData);

            const userId = loginData.id;

            const { data: user } = await api.get(`/auth/profile/me/${userId}`);
            // console.log("profile response data: ", user);

            return { loginData, user };

        } catch (error) {
            const message = error.response?.data?.message ||
                "Login failed. Please try again.";
            return rejectWithValue(message);
        }
    }
);

export const registerUser = createAsyncThunk(
    "auth/register",
    async (userData, { rejectWithValue }) => {
        try {
            const { data } = await api.post("/auth/register", userData);
            return data;
        } catch (error) {
            return rejectWithValue(error.response.data.message);
        }
    }
);
