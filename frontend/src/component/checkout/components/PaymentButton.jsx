import { Typography } from "@mui/material";
import React from "react";
import Button from "../../UI/Button";
import { useDispatch, useSelector } from "react-redux";
import { placeOrder } from "../../../State/order/orderThunk";

const fakeData = {
    addressId: 9,
    deliveryType: "DELIVERY",
    paymentMethod: "CARD",
    voucherCode: "WELCOME50",
    specialInstruction: "Please avoid calling, just ring the bell.",
};

export default function PaymentButton({ loading }) {
    const dispatch = useDispatch();
    const addressstore = useSelector((state) => state.address);

    console.log(addressstore);
    const handlePayment = () => {
        dispatch(placeOrder(fakeData));
    };

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
                {loading ? "Placing Order...." : "Place Order"}
            </Button>
        </div>
    );
}
