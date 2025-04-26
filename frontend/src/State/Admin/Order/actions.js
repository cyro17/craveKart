import { api } from "../../../component/config/api";


export function updateOrderStatus(reqData) {
    return async function (dispatch) {
        dispatch({ type: "order/updateOrderStatusRequest" });
        try {
            const { data: updatedOrder } = await api.put(
                `/api/admin/orders/${reqData.orderId}/${reqData.orderStatus}`, {}, {
                headers: {
                    Authorization: `Bearer ${reqData.jwt}`,
                },
            });
            dispatch({
                type: "order/updateOrderStatusSuccess",
                payload: updatedOrder
            });
        } catch (error) {
            dispatch({
                type: "order/updateOrderStatusFailure",
                payload: error
            });
        }
    }
}

export function fetchRestaurantsOrder(reqData) {
    return async function (dispatch) {
        dispatch({ type: "order/getRestaurantsOrderRequest" });
        try {
            const { data: orders } = await api.get(
                `/api/admin/order/restaurant/${reqData.restaurantId}`, {
                params: { order_status: reqData.orderStatus },
                headers: {
                    Authorization: `Bearer ${reqData.jwt}`,
                },
            });
            dispatch({
                type: "order/getRestaurantsOrderSuccess",
                payload: orders
            });
        } catch (error) {
            dispatch({
                type: "order/getRestaurantsOrderFailure",
                payload: error
            })
        }
    }
}