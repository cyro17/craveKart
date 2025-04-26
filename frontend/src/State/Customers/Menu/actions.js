import { api } from "../../../component/config/api";


export function createMenuItem(reqData) {
    return async function (dispatch) {
        dispatch({ type: "menu/createMenuItemRequest" });
        try {
            const { data } = await api.post("api/admin/food", reqData.menu,
                {
                    headers: {
                        Authorization: `Bearer ${reqData.jwt}`,
                    },
                });
            console.log("created menu ", data);
            dispatch({ type: "menu/createMenuItemSuccess", payload: data });
        } catch (error) {
            console.log("catch error ", error);
            dispatch({ type: "menu/createMenuItemFailure", payload: error });
        }
    }
}

export function getMenuItemsByRestaurantId(reqData) {
    return async function (dispatch) {
        dispatch({ type: "menu/getMenuItemsByRestaurantIdRequest" })
        try {
            const { data } = await api.get(
                `/api/food/restaurant/${reqData.restaurantId}?vegetarian=${reqData.vegetarian}&nonveg=${reqData.nonveg}
              &seasonal=${reqData.seasonal}&food_category=${reqData.foodCategory}`,
                {
                    headers: {
                        Authorization: `Bearer ${reqData.jwt}`,
                    },
                }
            );
            console.log("menu item by restaurants ", data);
            dispatch({ type: "menu/getMenuItemsByRestaurantIdSuccess", payload: data });
        } catch (error) {
            dispatch({ type: "menu/getMenuItemsByRestaurantIdFailure", payload: error });
        }
    }
}

export function searchMenuItem(reqData) {
    return async function (dispatch) {
        dispatch({ type: "menu/searchMenuItemRequest" });
        try {
            const { data } = await api.get(`api/food/search?name=${reqData.keyword}`, {
                headers: {
                    Authorization: `Bearer ${reqData.jwt}`,
                },
            });
            console.log("data ----------- ", data);
            dispatch({ type: "menu/searchMenuItemSuccess", payload: data });
        } catch (error) {
            dispatch({ type: "menu/searchMenuItemFailure", payload: error });
        }
    }
}

export function getAllIngredientsOfMenuItem(reqData) {
    return async function (dispatch) {
        dispatch({ type: "menu/getMenuItemsByRestaurantIdRequest" });
        try {
            const { data } = await api.get(
                `api/food/restaurant/${reqData.restaurantId}`,
                {
                    headers: {
                        Authorization: `Bearer ${reqData.jwt}`,
                    },
                }
            );
            console.log("menu item by restaurants ", data);
            dispatch({ type: "menu/getMenuItemsByRestaurantIdSuccess", payload: data });
        } catch (error) {
            dispatch({ type: "menu/getMenuItemsByRestaurantIdFailure", payload: error });
        }
    }
}

export function updateMenuItemsAvailability(reqData) {
    return async function (dispatch) {
        dispatch({ type: "menu/updateMenuItemsAvailabilityRequest" });
        try {
            const { data } = await api.put(`/api/admin/food/${reqData.foodId}`, {}, {
                headers: {
                    Authorization: `Bearer ${reqData.jwt}`,
                },
            });
            console.log("update menuItems Availability ", data);
            dispatch({ type: "menu/updateMenuItemsAvailabilitySuccess", payload: data });
        } catch (error) {
            console.log("error ", error)
            dispatch({
                type: "menu/updateMenuItemsAvailabilityFailure",
                payload: error,
            });
        }
    }
}

export function deleteFood(reqData) {
    return async function (dispatch) {
        dispatch({ type: "menu/deleteFoodRequest" });
        try {
            const { data } = await api.delete(`/api/admin/food/${reqData.foodId}`, {
                headers: {
                    Authorization: `Bearer ${reqData.jwt}`,
                },
            });
            console.log("delete food ", data);
            dispatch({ type: "menu/deleteFoodSuccesss", payload: data.foodId });
        } catch (error) {
            dispatch({ type: "menu/deleteFoodFailure", payload: error });
        }
    }
}