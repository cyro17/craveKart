import { CircularProgress, Typography } from "@mui/material";
import React, { useEffect, useState } from "react";
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
    const [countdown, setCountdown] = useState(3);
    useEffect(() => {
        dispatch(placeOrder());

        const interval = setInterval(() => {
            setCountdown((prev) => prev - 1);
        }, 1000);

        const timer = setTimeout(() => {
            navigate("/profile/orders", { replace: true });
        }, 3000);

        return () => {
            dispatch(resetOrder());
            clearTimeout(timer);
            clearInterval(interval);
        };
    }, [dispatch, navigate]);
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

                    <div className="mt-4 text-xl text-gray-500">
                        Redirecting to home in {countdown} second{" "}
                        {countdown > 1 ? "s" : ""}...
                    </div>
                    {/* <Button
                        variant="contained"
                        onClick={() => navigate("/profile/orders")}
                    >
                        View My Orders
                    </Button> */}
                </>
            )}
        </div>
    );
}
