import React, { useState } from "react";
import {
    PaymentElement,
    useElements,
    useStripe,
} from "@stripe/react-stripe-js";

export default function StripePaymentForm() {
    const [error, setError] = useState(null);
    const [paying, setPaying] = useState(false);

    const stripe = useStripe();
    const elements = useElements();

    async function handlePay({ orderId, onSuccess, onFailure }) {
        const { error, paymentIntent } = await stripe.confirmPayment({
            elements,
            confirmParams: {
                return_url: "http://localhost:3000/order/${orderId}/status",
            },
            redirect: "if_required",
        });

        if (error) {
            setError(error.message);
            onFailure?.(error.message);
            setPaying(false);
        } else if (paymentIntent?.status === "succeeded") {
            onSuccess?.();
        }
    }

    return (
        <div>
            <h3>Complete Payment</h3>
            <form action="" onSubmit={handlePay}>
                <PaymentElement />
                {error && <p>{error}</p>}
                <button type="submit" disabled={!stripe || paying}>
                    {paying ? (
                        <span>
                            <span>"Processing..."</span>
                        </span>
                    ) : (
                        "Pay Now"
                    )}
                </button>
            </form>
        </div>
    );
}
