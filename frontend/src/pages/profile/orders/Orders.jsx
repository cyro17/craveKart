import React, { act, useEffect, useMemo, useState } from "react";

import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { CircularProgress, Typography } from "@mui/material";
import OrderCard from "../../../component/profile/OrderCard";
import { fetchOrders } from "../../../State/order/orderThunk";

export default function Orders() {
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const { orders, loading, error } = useSelector((state) => state.order);
    console.log(orders);

    useEffect(() => {
        dispatch(fetchOrders());
    }, [dispatch]);

    console.log(orders);

    const { activeOrders, pastOrders } = useMemo(() => {
        const active = [];
        const past = [];

        orders?.forEach((order) => {
            if (
                ["DELIVERED", "CANCELLED", "FAILED"].includes(order.orderStatus)
            ) {
                past.push(order);
            } else active.push(order);
        });

        return { activeOrders: active, pastOrders: past };
    }, [orders]);

    if (loading) {
        return (
            <div className="flex justify-center mt-5">
                <CircularProgress />
            </div>
        );
    }

    if (error) {
        return <Typography color="error">Failed to load orders</Typography>;
    }

    return (
        <div>
            <Typography variant="h5" fontWeight="bold" mb={3}>
                Your Orders
            </Typography>

            {activeOrders.map((order) => (
                <OrderCard
                    key={order.id}
                    order={order}
                    pricing={order.pricing}
                    ship={order.delivery}
                    status={order.orderStatus}
                />
            ))}

            {pastOrders && (
                <>
                    <Typography variant="h6">Past Orders</Typography>
                    {pastOrders.map((order) => (
                        <OrderCard key={order.id} order={order} />
                    ))}
                </>
            )}
        </div>
    );
}
