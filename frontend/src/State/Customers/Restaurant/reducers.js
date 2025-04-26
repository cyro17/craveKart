export const reducers = {
    createRestaurantRequest: (state) => {
        state.loading = true;
        state.error = null;
    },
    getAllRestaurantRequest: (state) => {
        state.loading = true;
        state.error = null;
    },
    deleteRestaurantRequest: (state) => {
        state.loading = true;
        state.error = null;
    },
    updateRestaurantRequest: (state) => {
        state.loading = true;
        state.error = null;
    },
    getRestaurantByIdRequest: (state) => {
        state.loading = true;
        state.error = null;
    },
    createCategoryRequest: (state) => {
        state.loading = true;
        state.error = null;
    },
    getRestaurantCategoryRequest: (state) => {
        state.loading = true;
        state.error = null;
    },
    createRestaurantSuccess: (state, action) => {
        state.loading = false;
        state.usersRestaurant = action.payload;
    },
    getAllRestaurantSuccess: (state, action) => {
        state.loading = false;
        state.restaurants = action.payload;
    },
    getRestaurantByIdSuccess: (state, action) => {
        state.loading = false;
        state.restaurant = action.payload;
    },
    getRestaurantByUserIdSuccess: (state, action) => {
        state.loading = false;
        state.usersRestaurant = action.payload;
    },
    updateRestaurantStatusSuccess: (state, action) => {
        state.loading = false;
        state.restaurant = action.payload;
    },
    updateRestaurantSuccess: (state, action) => {
        state.loading = false;
        state.restaurant = action.payload;
    },
    deleteRestaurantSuccess: (state, action) => {
        state.error = null;
        state.loading = false;
        state.restaurants = state.restaurants.filter(
            (restaurant) => restaurant.id !== action.payload
        );
        state.usersRestaurant = state.usersRestaurant.filter(
            (restaurant) => restaurant.id !== action.payload
        );
    },
    createEventsSuccess: (state, action) => {
        state.loading = false;
        state.events = [...state.events, action.payload];
        state.restaurantsEvents = [...state.restaurantsEvents, action.payload];
    },
    getRestaurantsEventsSuccess: (state, action) => {
        state.loading = false;
        state.restaurantsEvents = action.payload;
    },

    deleteEventSuccess: (state, action) => {
        state.loading = false;
        state.events = state.events.filter((item) => item.id !== action.payload);
        state.restaurantsEvents = state.restaurantsEvents.filter(
            (item) => item.id !== action.payload
        );
    },

    createCategorySuccess: (state, action) => {
        state.loading = false;
        state.categories = [...state.categories, action.payload];
    },

    getRestaurantCategorySuccess: (state, action) => {
        state.loading = false;
        state.categories = action.payload;
    },

    createRestaurantFailure: (state, action) => {
        state.loading = false;
        state.error = action.payload;
    },

    getAllRestaurantFailure: (state, action) => {
        state.loading = false;
        state.error = action.payload;
    },

    deleteRestaurantFailure: (state, action) => {
        state.loading = false;
        state.error = action.payload;
    },

    updateRestaurantFailure: (state, action) => {
        state.loading = false;
        state.error = action.payload;
    },

    getRetaurantByIdFailure: (state, action) => {
        state.loading = false;
        state.error = action.payload;
    },

    createEventsFailure: (state, action) => {
        state.loading = false;
        state.error = action.payload;
    },

    createCategoryFailure: (state, action) => {
        state.loading = false;
        state.error = action.payload;
    },

    getRestaurantCategoryFailure: (state, action) => {
        state.loading = false;
        state.error = action.payload;
    },

};