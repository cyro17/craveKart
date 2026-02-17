import { CircularProgress, Typography } from "@mui/material";
import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Button from "../../UI/Button";
import { useDispatch, useSelector } from "react-redux";
import { placeOrder } from "../../../State/order/orderThunk";
import { resetOrder } from "../../../State/order/orderSlice";

export default function PaymentConfirmation() {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const { loading, error, orderId, success, totalPrice } = useSelector(
        (state) => state.order
    );

    useEffect(() => {
        dispatch(placeOrder());
        return () => {
            dispatch(resetOrder());
        };
    }, [dispatch]);
    return (
        <div className="text-black max-w-[50vw] mx-auto mt-8 p-4 text-center border border-solid border-slate-700 rounded-md shadow-lg">
            {loading ? (
                <CircularProgress />
            ) : (
                <>
                    <Typography variant="h5" className="mb-2 text-green-600">
                        ✅ Payment Successful
                    </Typography>
                    <Typography className="mb-3">
                        Thank you for your order! Your payment has been
                        completed.
                    </Typography>
                    <Button
                        variant="contained"
                        onClick={() => navigate("/profile/orders")}
                    >
                        View My Orders
                    </Button>
                </>
            )}
        </div>
    );
}
