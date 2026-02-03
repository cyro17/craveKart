import { createAsyncThunk } from "@reduxjs/toolkit";
import { api, API_URL } from "../../component/config/api";


export const loginUser = createAsyncThunk(
    "auth/loginUser",
    async ({ values, navigate }, { rejectWithValue }) => {
        try {
            const { data } = await
                api.post(`${API_URL}/auth/signin`, values);
            console.log("post request data: ", data);
            if (data.jwt) {
                localStorage.setItem("jwt", data.jwt);
                navigate("/");
            }
            return data;
        } catch (error) {
            return rejectWithValue(
                error.response && error.response.data.message ?
                    error.response.data.message : error.message
            );
        }
    }
);

export const registerUser = createAsyncThunk(
    "auth/registerUser",
    async ({ userData, navigate }, { rejectWithValue }) => {
        try {
            const { data } = await api.post(`${API_URL}/auth/signup`, userData);
            navigate("/account/login");
            return data;
        } catch (err) {
            return rejectWithValue(err.response && err.response.data.message ?
                err.response.data.message : err.message);
        }
    }
);