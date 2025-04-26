import { api } from "../../../component/config/api";


export function getIngredientsOfRestaurant(reqData) {
    return async function (dispatch) {
        try {
            const response = await api.get(`/api/admin/ingredients/restaurant/${reqData.id}`, {
                headers: {
                    Authorization: `Bearer ${reqData.jwt}`,
                },
            });
            console.log("get all ingredients ", response.data)
            dispatch({
                type: "admin/ingredients/getIngredients",
                payload: response.data // Assuming the response contains the ingredients data
            });
        } catch (error) {
            console.log("error", error)
            // Handle error, dispatch an error action, etc.
        }

    }
}


export function createIngredient(reqData) {
    return async (dispatch) => {
        try {
            const response = await api.post(`/api/admin/ingredients`, reqData.data, {
                headers: {
                    Authorization: `Bearer ${reqData.jwt}`,
                },
            });
            console.log("create ingredients ", response.data)
            dispatch({
                type: "admin/ingredients/createIngredientsSuccess",
                payload: response.data
            });
        } catch (error) {
            console.log("error", error)
            // Handle error, dispatch an error action, etc.
        }
    };
};

export function getIngredientCategory(reqData) {
    return async function (dispatch) {
        const { id, jwt } = reqData;
        try {
            const response = await api.get(`/api/admin/ingredients/restaurant/${id}/category`, {
                headers: {
                    Authorization: `Bearer ${jwt}`,
                },
            });
            console.log("get ingredients category", response.data)
            dispatch({
                type: "admin/ingredients/getIngredientCategory",
                payload: response.data
            });
        } catch (error) {
            console.log("error", error)

        }
    }
};

export function updateStockOfIngredient(reqData) {
    return async function (dispatch) {

    }
}