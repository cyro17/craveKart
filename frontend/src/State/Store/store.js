import { configureStore } from "@reduxjs/toolkit";
import { authReducer } from "../Authentication/authSlice";
import { restaurantReducer } from "../Restaurant/restaurantSlice";
import { cartReducer } from "../cart/cartSlice";
import { uiReducer } from "../ui/uiSlice";


const store = configureStore({
    reducer: {
        auth: authReducer,
        restaurant: restaurantReducer,
        cart: cartReducer,
        ui: uiReducer
    }
})

export default store;