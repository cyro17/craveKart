

export const reducers = {
    findCartRequest(state, actions) {
        state.loading = true;
        state.error = null;
    },

    getAllCartItemsRequest(state, actions) {
        state.loading = true;
        state.error = null;
    },

    addItemtoCartRequest(state, actions) {
        state.loading = true;
        state.error = null;
    },

    updateCartItemRequest(state, actions) {
        state.loading = true;
        state.error = null;
    },

    removeCartItemrequest(state, actions) {
        state.loading = true;
        state.error = null;
    },

    findCartSuccess(state, actions) {
        state.loading = false;
        state.cart = actions.payload;
        state.cartItems = actions.payload.items;
    },

    clearCartSuccess(state, actions) {
        state.loading = false;
        state.cart = actions.payload;
        state.cartItems = actions.payload.items;
    },

    addItemToCartSuccess(state, actions) {
        state.loading = false;
        state.cartItems = [actions.payload, ...state.cartItems];
    },

    updateCartItemSuccess(state, actions) {
        state.loading = false;
        state.cartItems = state.cartItems.map(
            (item) => item.id === actions.payload.id ? actions.payload : item);
    },

    removeCartItemSuccess(state, actions) {
        state.loading = false;
        state.cartItems = state.cartItems.filter(
            (item) => item.id !== actions.payload)
    },

    findCartFailure(state, actions) {
        state.loading = false;
        state.error = actions.payload;
    },

    updateCartItemFailure(state, actions) {
        state.loading = false;
        state.error = actions.payload;
    },

    removeCartItemFailure(state, actions) {
        state.loading = false;
        state.error = actions.payload;
    },

    logout(state, actions) {
        localStorage.removeItem("jwt");
        state.cartItems = [];
        state.cart = null;
        state.success = "logout sucessfull ! ";
    }
};