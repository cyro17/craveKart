

export const reducers = {
    createMenuItemRequest(state, action) {
        state.loading = true;
        state.error = null;
        state.message = "Food Created Succesfully."
    },
    getMenuItemsByRestaurantIdRequest(state, action) {
        state.loading = true;
        state.error = null;
        state.message = "Food Created Succesfully."
    },

    deleteMenuItemRequest(state, action) {
        state.loading = true;
        state.error = null;
        state.message = "Food Created Succesfully."
    },

    searchMenuItemRequest(state, action) {
        state.loading = true;
        state.error = null;
        state.message = "Food Created Succesfully."
    },

    updateMenuItemsAvailabilityRequest(state, action) {
        state.loading = true;
        state.error = null;
        state.message = "Food Created Succesfully."
    },

    createMenuItemSuccess(state, action) {
        state.loading = true;
        state.menuItems = [...state.menuItems, action.payload];
        state.message = "Food Created Succesfully."
    },

    getMenuItemsByRestaurantIdSuccess(state, action) {
        state.loading = false;
        state.menuItems = state.menuItems.filter(
            menuItem => menuItem.id !== action.payload
        );
    },

    deleteMenuItemSuccess(state, action) {
        state.loading = false;
        state.menuItems = state.menuItems.filter(
            (menuItem) => menuItem.id !== action.payload
        );
    },

    updateMenuItemsAvailabilitySuccess(state, action) {
        state.loading = false;
        state.menuItems = state.menuItems.map((menuItem) =>
            menuItem.id === action.payload.id ? action.payload : menuItem
        );
    },

    searchMenuItemSuccess(state, action) {
        state.loading = false;
        state.search = action.payload;
    },

    createMenuItemFailure(state, action) {
        state.loading = false;
        state.error = action.payload;
        state.message = null;
    },

    getMenuItemsByRestaurantIdFailure(state, action) {
        state.loading = false;
        state.error = action.payload;
        state.message = null;
    },

    deleteMenuItemFailure(state, action) {
        state.loading = false;
        state.error = action.payload;
        state.message = null;
    },

    searchMenuItemFailure(state, action) {
        state.loading = false;
        state.error = action.payload;
        state.message = null;
    },

    updateMenuItemsAvailabilityFailure(state, action) {
        state.loading = false;
        state.error = action.payload;
        state.message = null;
    },
}

