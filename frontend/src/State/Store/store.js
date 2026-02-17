import { configureStore } from "@reduxjs/toolkit";
import { authReducer } from "../Authentication/authSlice";
import { restaurantReducer } from "../Restaurant/restaurantSlice";
import { cartReducer } from "../cart/cartSlice";
import { uiReducer } from "../ui/uiSlice";
import { addressReducer } from "../address/addressSlice";
import { paymentReducer } from "../payment/paymentSlice";
import { orderReducer } from "../order/orderSlice";


const store = configureStore({
    reducer: {
        auth: authReducer,
        restaurant: restaurantReducer,
        cart: cartReducer,
        ui: uiReducer,
        address: addressReducer,
        payment: paymentReducer,
        order: orderReducer
    }
})

export default store;