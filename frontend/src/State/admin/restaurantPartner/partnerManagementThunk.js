import { createAsyncThunk, isRejectedWithValue } from "@reduxjs/toolkit";
import api from "../../../config/api";


export const fetchPendingPartners = createAsyncThunk(
    "admin/partnerManagement/fetchPending",
    async (__dirname, { rejectWithValue }) => {
        try {
            const { data } = await api.get("/admin/restaurantPartners/pending");
            return data;
        } catch (err) {
            return rejectWithValue(err.response?.data || err.message);
        }
    }
)

export const fetchAllPartners = createAsyncThunk(
    "/admin/partnerManagement/fetchAll",
    async (status = null, { rejectWithValue }) => {
        try {
            const params = status ? { status } : {};

            const { data } = await api.get("/admin/restaurantPartners", { params })
            console.log("data: ", data);
            return data;
        } catch (err) {
            return rejectWithValue(err.response?.data || err.message);
        }
    }
)


export const fetchPartnerById = createAsyncThunk(
    "/admin/partnerManagement/fetchById",
    async (partnerId, { rejectWithValue }) => {
        try {
            const { data } = await api.get(`/admin/restaurantPartners/${partnerId}`);
            return data;
        } catch (err) {
            return rejectWithValue(err.response?.data || err.message);
        }
    }
)

export const approvePartner = createAsyncThunk(
    "admin/partnerManagement/approve",
    async (partnerId, { rejectWithValue }) => {
        try {
            await api.patch(`/admin/restaurantPartners/${partnerId}/approve`);
            return partnerId;
        } catch (err) {
            return rejectWithValue(err.response?.data || err.message);
        }
    }
);

export const rejectPartner = createAsyncThunk(
    "admin/partnerManagement/reject",
    async ({ partnerId, reason }, { rejectWithValue }) => {
        try {
            await api.patch(
                `/admin/restaurantPartners/${partnerId}/reject`,
                null,
                { params: { reason } }
            );
            return partnerId;
        } catch (err) {
            return rejectWithValue(err.response?.data || err.message);
        }
    }
);

export const suspendPartner = createAsyncThunk(
    "admin/partnerManagement/suspend",
    async ({ partnerId, reason }, { rejectWithValue }) => {
        try {
            await api.patch(
                `/admin/restaurantPartners/${partnerId}/suspend`,
                null,
                { params: { reason } }
            );
            return partnerId;
        } catch (err) {
            return rejectWithValue(err.response?.data || err.message);
        }
    }
);

export const reactivatePartner = createAsyncThunk(
    "admin/partnerManagement/reactivate",
    async (partnerId, { rejectWithValue }) => {
        try {
            await api.patch(`/admin/restaurantPartners/${partnerId}/reactivate`);
            return partnerId;
        } catch (err) {
            return rejectWithValue(err.response?.data || err.message);
        }
    }
);

