import { api } from "../../../component/config/api";


export function createOrder(reqData) {
    return async function (dispatch) {
        dispatch({ type: "order/createOrderRequest" });
        try {
            const { data } = await api.post('/api/order', reqData.order, {
                headers: {
                    Authorization: `Bearer ${reqData.jwt}`,
                },
            });
            if (data.payment_url) {
                window.location.href = data.payment_url;
            }
            console.log("created order data", data)
            dispatch({ type: "order/createOrderSuccess", payload: data });
        } catch (error) {
            console.log("error ", error);
            dispatch({ type: "order/createOrderFailure", payload: error });
        }
    };
};

export function getUsersOrders(jwt) {
    return async function (dispatch) {
        dispatch({ type: "order/getUsersOrderRequest" });
        try {
            const { data } = await api.get(`/api/order/user`, {
                headers: {
                    Authorization: `Bearer ${jwt}`,
                },
            });
            console.log("users order ", data)
            dispatch({ type: "order/getUsersOrderSuccess", payload: data });
        } catch (error) {
            dispatch({ type: "order/getUsersOrderFailure", payload: error });
        }
    }
}

export function getUsersNotification() {
    return async function (dispatch) {
        dispatch({ type: "order/createOrderRequest" });
        try {
            const { data } = await api.get(`/api/notifications`);
            dispatch({
                type: "order/getUsersNotificationsSuccess",
                payload: data
            });
        } catch (error) {
            dispatch({
                type: "order/getUsersNotificationsFailure",
                payload: error
            });
        }
    }
}