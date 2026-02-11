import { configureStore } from "@reduxjs/toolkit";
import { authReducer } from "../Authentication/authSlice";
import { restaurantReducer } from "../Restaurant/restaurantSlice";


const store = configureStore({
    reducer: {
        auth: authReducer,
        restaurant: restaurantReducer,
    }
})

export default store;