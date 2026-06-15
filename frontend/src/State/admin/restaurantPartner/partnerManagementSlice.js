import { createSlice } from '@reduxjs/toolkit';
import { approvePartner, fetchAllPartners, fetchPartnerById, fetchPendingPartners } from './partnerManagementThunk';

const initialState = {
    pending: [],
    all: [],
    selected: null,
    loading: false,
    actionLoading: null,
    error: null,
    successMessage: null
};

const handlePending = state => {
    state.loading = true;
    state.error = null;
}


const handleRejected = (state, action) => {
    state.loading = false;
    state.error = action.payload || "Something went wrong";
};


createSlice({
    name: "admin/partnerManagement",
    initialState,

    extraReducers: (builder) => {
        builder
            .addCase(fetchPendingPartners.pending, handlePending)
            .addCase(fetchPendingPartners.rejected, handleRejected)
            .addCase(fetchPendingPartners.fulfilled, (state, action) => {
                state.loading = false;
                state.pending = action.payload;
            })

            .addCase(fetchAllPartners.pending, handlePending)
            .addCase(fetchAllPartners.rejected, handleRejected)
            .addCase(fetchAllPartners.fulfilled, (state, action) => {
                state.loading = false;
                state.all = action.payload;
            })

            .addCase(fetchPartnerById.pending, handlePending)
            .addCase(fetchPartnerById.rejected, handleRejected)
            .addCase(fetchPartnerById.fulfilled, (state, action) => {
                state.loading = false;
                state.selected = action.payload;
            })

            .addCase(approvePartner.pending, (state, action) => {
                console.log("action meta arg in approve partner slice", action.meta.arg);
                state.actionLoading = action.meta.arg;
            })



    }
}
)