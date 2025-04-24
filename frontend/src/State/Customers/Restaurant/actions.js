import { api } from "../../../component/config/api";

export function getAllRestaurant(token) {
    return async function (dispatch) {
        dispatch({ type: "restaurant/getAllRestaurantRequest" });
        try {
            const { data } = await api.get("/api/restaurants", {
                headers: {
                    Authorization: `Bearer ${token}`,
                }
            });
            dispatch({ type: "restaurant/getAllRestaurantSuccess", payload: data });
            console.log("all restaurant ", data);
        } catch (error) {
            console.log(error);
        }
    }
}