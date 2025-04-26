import { applyMiddleware, combineReducers, legacy_createStore } from "redux";

import { authReducer } from "../Admin/Authentication/authSlice";
import { thunk } from "redux-thunk";
import { restaurantReducer } from "../Customers/Restaurant/restaurantSlice";
import { orderReducer } from "../Customers/Orders/orderSlice";
import { menuReducer } from "../Customers/Menu/menuSlice";
import { cartReducer } from "../Customers/Cart/cartSlice";
import { ingredientsReducer } from "../Admin/Ingredients/ingredientsSlice";


const rootReducer = combineReducers({
    auth: authReducer,
    restaurant: restaurantReducer,
    order: orderReducer,
    menu: menuReducer,
    cart: cartReducer,

    // admin
    ingredients: ingredientsReducer,
    adminOrder: orderReducer,

    //super admin

})

const store = legacy_createStore(
    rootReducer,
    applyMiddleware(thunk)
);

export default store;




