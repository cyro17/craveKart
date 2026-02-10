import { createAsyncThunk } from "@reduxjs/toolkit";
import { api } from "../../component/config/api";

export const loginUser = createAsyncThunk(
    "auth/login",
    async ({ values, navigate }, { rejectWithValue }) => {
        try {
            const { data } = await api.post("/auth/login", values);
            localStorage.setItem("token", data.token);
            navigate("/");
            return data;
        } catch (error) {
            return rejectWithValue(error.response.data.message);
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
