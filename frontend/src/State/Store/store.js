import { configureStore } from "@reduxjs/toolkit";
import { authReducer } from "../Authentication/authSlice";
import { restaurantReducer } from "../Restaurant/restaurantSlice";
import { cartReducer } from "../cart/cartSlice";


const store = configureStore({
    reducer: {
        auth: authReducer,
        restaurant: restaurantReducer,
        cart: cartReducer
    }
})

export default store;