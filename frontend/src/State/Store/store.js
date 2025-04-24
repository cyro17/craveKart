import { applyMiddleware, combineReducers, legacy_createStore } from "redux";

import { authReducer } from "../Authentication/authSlice";
import { thunk } from "redux-thunk";
import { restaurantReducer } from "../Customers/Restaurant/restaurantSlice";


const rootReducer = combineReducers({
    auth: authReducer,
    restaurant: restaurantReducer,
})

const store = legacy_createStore(
    rootReducer,
    applyMiddleware(thunk)
);

export default store;




