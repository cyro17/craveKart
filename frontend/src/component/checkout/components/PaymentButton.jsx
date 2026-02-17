import { Typography } from "@mui/material";
import React, { useEffect } from "react";
import Button from "../../UI/Button";
import { useDispatch, useSelector } from "react-redux";
import { resetPayment } from "../../../State/payment/paymentSlice";
import { createCheckoutSession } from "../../../State/payment/paymentThunk";

const fakeData = {
    amount: 100000,
    quantity: 1,
    name: "Veg Burger",
    currency: "INR",
};

export default function PaymentButton() {
    const dispatch = useDispatch();
    const { loading, error, sessionUrl } = useSelector(
        (state) => state.payment
    );

    const handlePayment = () => {
        dispatch(createCheckoutSession(fakeData));
    };

    useEffect(() => {
        if (sessionUrl) {
            window.location.href = sessionUrl;
            dispatch(resetPayment());
        }
    }, [sessionUrl, dispatch]);

    return (
        <div className="m-auto w-full ">
            <Typography>Complete Your Payment</Typography>
            <Button
                fullWidth
                disabled={loading}
                variant="contained"
                onClick={handlePayment}
                className="py-3"
            >
                {loading ? "Redirecting..." : "Pay Now"}
            </Button>
            {error && (
                <Typography className="mt-2 text-red-500">{error}</Typography>
            )}
        </div>
    );
}
