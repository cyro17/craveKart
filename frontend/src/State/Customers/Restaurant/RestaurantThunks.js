
import { createAsyncThunk } from "@reduxjs/toolkit";
import { api } from "../../../component/config/api";

export const getAllRestaurant = createAsyncThunk(
    "restaurant/getAllRestaurant",
    async (token, { rejectWithValue }) => {
        try {
            const { data } = await api.get(`/admin/restaurants`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                }
            });
            return data;
        } catch (error) {
            return rejectWithValue(error.response && error.response.data.message ?
                error.response.data.message : error.message);
        }
    }

)