import { api } from "../../../component/config/api";

export function findCart(token) {
    return async function (dispatch) {
        dispatch({ type: "cart/findCartRequest" });
        try {
            const response = await api.get(`/api/cart/`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            dispatch({ type: "cart/findCartSuccess", payload: response.data });
        } catch (error) {
            dispatch({ type: "cart/findCartFailure", payload: error });
        }
    }
}


export function getAllCartItems(reqData) {
    return async function (dispatch) {
        dispatch({ type: "cart/getAllCartItemsRequest" });
        try {
            const response = await api.get(`/api/carts/${reqData.cartId}/items`, {
                headers: {
                    Authorization: `Bearer ${reqData.token}`,
                },
            });
            dispatch({ type: "cart/getAllCartItemsSuccess", payload: response.data });
        } catch (error) {
            dispatch({ type: "cart/getAllCartItemsFailure", payload: error });
        }
    };
};

export function addItemToCart(reqData) {
    return async function (dispatch) {
        dispatch({ type: "cart/addItemtoCartRequest" });
        try {
            const { data } = await api.put(`/api/cart/add`, reqData.cartItem, {
                headers: {
                    Authorization: `Bearer ${reqData.token}`,
                },
            });
            console.log("add item to cart ", data)
            dispatch({ type: "cart/addItemtoCartSuccess", payload: data });

        } catch (error) {
            console.log("catch error ", error)
            dispatch({ type: "cart/addItemtoCartFailure", payload: error.message });
        }
    };
}

export function updateCartItem(reqData) {
    return async function (dispatch) {
        dispatch({ type: "cart/updateCartItemRequest" });
        try {
            const { data } = await api.put(`/api/cart-item/update`, reqData.data, {
                headers: {
                    Authorization: `Bearer ${reqData.jwt}`,
                },
            });
            console.log("update cartItem ", data)
            dispatch({ type: "cart/updateCartItemSuccess", payload: data });

        } catch (error) {
            console.log("catch error ", error)
            dispatch({ type: "cart/updateCartItemFailure", payload: error.message });
        }
    }
}

export function clearCart(reqData) {
    return async function (dispatch) {
        dispatch({ type: "cart/clearCartRequest" });
        try {
            const { data } = await api.put(`/api/cart/clear`, {}, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("jwt")}`,
                },
            });

            dispatch({ type: "cart/clearCartSuccess", payload: data });
            console.log("clear cart ", data)
        } catch (error) {
            console.log("catch error ", error)
            dispatch({ type: "cart/clearCartFailure", payload: error.message });
        }
    }
}

