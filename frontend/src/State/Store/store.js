import { applyMiddleware, combineReducers, legacy_createStore } from "redux";

import { authReducer } from "../Authentication/authSlice";
import { thunk } from "redux-thunk";
import { restaurantReducer } from "../Customers/Restaurant/restaurantSlice";
import { orderReducer } from "../Customers/Orders/orderSlice";
import { menuReducer } from "../Customers/Menu/menuSlice";
import { cartReducer } from "../Customers/Cart/cartSlice";
import { ingredientsReducer } from "../Admin/Ingredients/ingredientsSlice";
import { restaurantsOrderReducer } from "../Admin/Order/orderSlice";
import { configureStore } from "@reduxjs/toolkit";


const store = configureStore({
    reducer: {
        auth: authReducer,
        restaurant: restaurantReducer,
    },
    devTools: true
});

export default store;




