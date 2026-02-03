import { api } from "../../../component/config/api";

export function getAllRestaurant(token) {
    return async function (dispatch) {
        dispatch({ type: "restaurant/getAllRestaurantRequest" });
        try {
            const { data } = await api.get("/customer/restaurants", {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            console.log(data);
            dispatch({ type: "restaurant/getAllRestaurantSuccess", payload: data });
        } catch (error) {
            dispatch({ type: "restaurant/getAllRestaurantFailure", payload: error });
        }
    }
}

export function getRestaurantById(reqData) {
    return async function (dispatch) {
        dispatch({ type: "restaurant/getRestaurantByIdRequest" });
        try {
            const response = await api.get(`/api/restaurants/${reqData.restaurantId}`, {
                headers: {
                    Authorization: `Bearer ${reqData.jwt}`,
                }
            })
            dispatch({ type: "restaurant/getRestaurantByIdSuccess", payload: response.data })
        } catch (error) {
            console.log(error);
            dispatch({ type: "restaurant/getRestaurantByIdFailure", payload: error.message })
        }
    }
}

export function getRestaurantByUserId(jwt) {
    return async function (dispatch) {
        dispatch({ type: "restaurant/getRestaurantByUserIdRequest" });
        try {
            const { data } = await api.get(`/admin/restaurants/user`, {
                headers: {
                    Authorization: `Bearer ${jwt}`,
                }
            })
            console.log("get resraurant by user id: ", data);
            dispatch({ type: "restaurant/getRestaurantByUserIdSuccess", payload: data ? data : null })
        } catch (error) {
            console.log(error);
            dispatch({
                type: "restaurant/getRestaurantByUserIdFailure",
                payload: error.message
            })
        }
    }
}

export function createRestaurant(reqData) {
    return async function (dispatch) {
        dispatch({ type: "restaurant/createRestaurantRequest" });
        try {
            const { data } = await api.post(`/admin/restaurants`, reqData.data, {
                headers: {
                    Authorization: `Bearer ${reqData.token}`,
                }
            });
            console.log(data)
            dispatch({ type: "restaurant/createRestaurantSuccess", payload: data });
        } catch (error) {
            dispatch({ type: "restaurant/createRestaurantFailure", payload: error.message });
        }
    }
}

export function deleteRestaurant(reqData) {
    return async function (dispatch) {
        dispatch({ type: "restaurant/deleteRestaurantRequest" });
        try {
            const response = await api.delete(`/admin/restaurants/${reqData.restaurantId}`, {
                headers: {
                    Authorization: `Bearer ${reqData.jwt}`,
                }
            });
            dispatch({ type: "restaurant/deleteRestaurantSuccess", payload: response.data });
        } catch (error) {
            dispatch({ type: "restaurant/deleteRestaurantFailure", payload: error.message });
        }
    }
}

export function updateRestaurant(reqData) {
    return async function (dispatch) {
        dispatch({ type: "restaurant/updateRestaurantRequest" });
        try {
            const { data } =
                await api.put(`/admin/restaurants/${reqData.restaurantId}`, reqData.data, {
                    headers: {
                        Authorization: `Bearer ${reqData.jwt}`,
                    }
                });
            dispatch({ type: "restaurant/updateRestaurantSuccess", payload: data });
        } catch (error) {
            dispatch({ type: "restaurant/updateRestaurantFailure", payload: error.message });
        }
    }
}

export function updateRestaurantStatus(reqData) {
    return async function (dispatch) {
        dispatch({ type: "restaurant/updateRestaurantStatusRequest" });
        try {
            const res = await api.put(
                `api/admin/restaurants/${reqData.restaurantId}/status`,
                {},
                {
                    headers: {
                        Authorization: `Bearer ${reqData.jwt}`
                    }
                }
            );
            console.log("data: ", res.data);
            dispatch({ type: "restaurants/updateRestaurantStatusSuccess", payload: res.data });
        } catch (err) {
            dispatch({ type: "restaurants/updateRestaurantStatusFailure", payload: err.message });
        }
    }
}

export function createEvent(reqData) {
    return async function (dispatch) {
        dispatch("/restaurant/createEventsRequest");
        try {
            const res = await api.post(
                `/admin/events/restaurant/${reqData.restaurantId}`,
                reqData.data,
                {
                    headers: {
                        Authorization: `Bearer ${reqData.jwt}`
                    }
                });
            dispatch({ type: "restaurant/createEventsSuccess", payload: res.data })
        } catch (err) {
            dispatch({ type: "restaurant/createEventsFailure", payload: err });
        }
    }
}

export function getAllEvents(reqData) {
    return async function (dispatch) {
        dispatch({ type: "restaurant/getAllEventsRequest" });
        try {
            const res = await api.get(
                `/events`, {
                headers: {
                    Authorization: `Bearer ${reqData.jwt}`
                }
            });
            dispatch({ type: "restaurant/getAllEventsSuccess", payload: res.data });
        } catch (err) {
            dispatch({ type: "restaurant/getAllEventsFailure", payload: err.message });
        }
    }
}

export function deleteEvents(reqData) {
    return async function (dispatch) {
        dispatch({ type: "restaurant/deleteEventRequest" });
        try {
            const res = await api.delete(`/admin/events/${reqData.eventId}`, {
                headers: {
                    Authorization: `Bearer ${reqData.jwt}`
                }
            });
            dispatch({ type: "restaurant/deleteEventSuccess", payload: res.eventId });
        } catch (err) {
            dispatch({ type: "restaurant/deleteEventFailure", payload: err.message });
        }
    }
}

export function getRestaurantEvents(reqData) {
    return async function (dispatch) {
        dispatch({ type: "restaurant/getRestaurantEventRequest" });
        try {
            const res = await api.get(`/admin/events/restaurant/${reqData.restaurantId}`, {
                headers: {
                    Authorization: `Bearer ${reqData.jwt}`
                }
            });
            dispatch({ type: "restaurant/getRestaurantEventSuccess", payload: res.data });
        } catch (err) {
            dispatch({ type: "restaurant/getRestaurantEventFailure", payload: err.message });
        }
    }
}

export function createCategory(reqData) {
    return async function (dispatch) {
        dispatch({ type: "restaurant/createCategoryReqeust" });
        try {
            const res = await api.post(`/admin/category`, reqData, {
                headers: {
                    Authorization: `Bearer ${reqData.jwt}`
                }
            });
            dispatch({ type: "restaurant/createCategorySuccess", payload: res.data });
        } catch (err) {
            dispatch({ type: "restaurant/createCategoryFailure", payload: err.message });
        }
    }
}

export function getRestaurantCategory(reqData) {
    return async function (dispatch) {
        dispatch({ type: "restaurant/getRestaurantCategoryRequest" });
        try {
            const res = await api.get(`/category/restaurant/${reqData.restaurantId}`, {
                headers: {
                    Authorization: `Bearer ${reqData.jwt}`
                }
            });
            dispatch({ type: "restaurant/getRestaurantCategorySuccess", payload: res.data });
        } catch (err) {
            dispatch({ type: "restaurant/getRestaurantCategoryFailure", payload: err.message });
        }
    }
}