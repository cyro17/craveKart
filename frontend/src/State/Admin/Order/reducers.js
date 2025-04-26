
export const reducers = {
    getRestaurantsOrderRequest(state, action) {
        state.loading = true;
        state.error = null;
    },

    updateOrderStatusRequest(state, action) {
        state.loading = true;
        state.error = null;
    },

    getRestaurantsOrderSuccess(state, action) {
        state.loading = false;
        state.orders = action.payload;
    },

    updateOrderStatusSuccess(state, action) {
        const updatedOrders = state.orders.map((order) =>
            order.id === action.payload.id ?
                action.payload : order);
        state.loading = false;
        state.orders = updatedOrders;
    },

    getRestaurantsOrderFailure(state, action) {
        state.loading = false;
        state.error = action.payload;
    },

    updateOrderStatusFailure(state, action) {
        state.loading = false;
        state.error = action.payload;
    }
}