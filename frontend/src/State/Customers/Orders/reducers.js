export const reducers = {
    getUsersOrdersRequest(state, action) {
        state.loading = true;
        state.error = null;
    },

    getUsersOrdersSuccess(state, action) {
        state.loading = false;
        state.orders = action.payload;
    },

    getUsersOrdersFailure(state, action) {
        state.loading = false;
        state.error = action.payload;
    },

    getUsersNotificationSuccess(state, action) {
        state.loading = false;
        state.notifications = action.payload;
        state.error = null;
    }
};