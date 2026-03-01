import React, { useState } from "react";
import { motion } from "motion/react";
import {
    PaymentElement,
    useElements,
    useStripe,
} from "@stripe/react-stripe-js";
import { useDispatch } from "react-redux";
import { resetOrder } from "../../../State/order/orderSlice";

export default function CheckoutPayment({ orderId, onSuccess, onFailure }) {
    console.log("************************", orderId);

    const stripe = useStripe();
    const elements = useElements();
    const dispatch = useDispatch();
    const [error, setError] = useState(null);
    const [paying, setPaying] = useState(false);

    const handlePay = async (e) => {
        e.preventDefault();
        if (!stripe || !elements) return;

        console.log(e.target.values);

        setPaying(true);
        setError(null);

        // This is required — validates and finalizes the form before confirming
        const { error: submitError } = await elements.submit();
        if (submitError) {
            setError(submitError.message);
            onFailure?.(submitError.message);
            setPaying(false);
            return;
        }

        const { error: stripeError, paymentIntent } =
            await stripe.confirmPayment({
                elements,
                confirmParams: {
                    return_url: `${window.location.origin}/`,
                },
                redirect: "if_required",
            });

        // Add these
        console.log("stripeError:", stripeError);
        console.log("paymentIntent status:", paymentIntent?.status);
        console.log("paymentIntent next_action:", paymentIntent?.next_action);

        if (stripeError) {
            setError(stripeError.message);
            onFailure?.(stripeError.message);
            setPaying(false);
        } else if (paymentIntent?.status === "succeeded") {
            onSuccess?.();
        } else {
            setError(`Unexpected status: ${paymentIntent?.status}`);
            setPaying(false);
        }
    };

    return (
        <div className="">
            {/* Header */}
            <div className="">
                <div>
                    HEADER
                    <div>
                        <svg
                            width="20"
                            height="20"
                            viewBox="0 0 24 24"
                            fill="none"
                        >
                            <rect
                                x="2"
                                y="5"
                                width="20"
                                height="15"
                                rx="3"
                                stroke="#F43F5E"
                                strokeWidth="1.5"
                            />
                            <path
                                d="M2 10H22M6 15H10M13 15H15"
                                stroke="#F43F5E"
                                strokeWidth="1.5"
                                strokeLinecap="round"
                            />
                        </svg>
                    </div>
                </div>
            </div>

            {/* Stripe badge */}
            <div className="flex items-center">
                <svg width="12" height="12" viewBox="0 0 24 24 " fill="none">
                    <path
                        d="M12 2L4 6V12C4 16.4 7.4 20.5 12 22C16.6 20.5 20 16.4 20 12V6L12 2Z"
                        fill="#635BFF"
                    />
                </svg>
                <span className="text-xs text-gray-500 font-medium">
                    Secured by Stripe
                </span>
            </div>

            {/* Stripe form */}
            <form onSubmit={handlePay} className="space-y-6">
                <div>
                    <PaymentElement
                        options={{
                            layout: "tabs",
                            paymentMethodOrder: ["card"],
                            fields: { billingDetails: { name: "auto" } },
                        }}
                    />
                </div>

                {/* error  */}
                {error && (
                    <motion.div>
                        <svg
                            width="16"
                            height="16"
                            viewBox="0 0 24 24"
                            fill="none"
                            className="flex-shrink-0 mt-0.5"
                        >
                            <circle
                                cx="12"
                                cy="12"
                                r="10"
                                stroke="#EF4444"
                                strokeWidth="1.5"
                            />
                            <path
                                d="M12 8V12M12 16V16.5"
                                stroke="#EF4444"
                                strokeWidth="2"
                                strokeLinecap="round"
                            />
                        </svg>
                        {error}
                    </motion.div>
                )}

                {/* Amount hint */}
                <div>
                    <span>Amount to pay</span>
                    <span className="font-bold text-base text-black">
                        Check order summary {"->"}
                    </span>
                </div>

                {/* submit */}
                <div className="flex gap-3">
                    <button
                        type="button"
                        onClick={() => dispatch(resetOrder())}
                        className="w-[30%] px-5 py-3 rounded-xl border-2 border-gray-200 text-sm"
                    >
                        {"<-"} Back
                    </button>
                    <button
                        className="flex-1"
                        type="submit"
                        disabled={!stripe || paying}
                    >
                        {paying ? (
                            <span>Processing Payment</span>
                        ) : (
                            <span>Pay Now</span>
                        )}
                    </button>
                </div>

                {/* Trusted */}
                <div className="flex items-center justify-center gap-4 mt-5 text-xs text-gray-400">
                    <span className="flex items-center gap-1 text-xs text-gray-500 font-medium">
                        <svg
                            width="12"
                            height="12"
                            viewBox="0 0 24 24"
                            fill="none"
                        >
                            <path
                                d="M12 2L4 6V12C4 16.4 7.4 20.5 12 22C16.6 20.5 20 16.4 20 12V6L12 2Z"
                                stroke="#9CA3AF"
                                strokeWidth="1.5"
                            />
                        </svg>
                        SSL Encrypted
                    </span>
                    <span className="w-1 h-1 rounded-full bg-gray-200" />
                    <span className="flex items-center gap-1">
                        <svg
                            width="12"
                            height="12"
                            viewBox="0 0 24 24"
                            fill="none"
                        >
                            <rect
                                x="3"
                                y="11"
                                width="18"
                                height="11"
                                rx="2"
                                stroke="#9CA3AF"
                                strokeWidth="1.5"
                            />
                            <path
                                d="M7 11V7C7 4.79 8.79 3 11 3H13C15.21 3 17 4.79 17 7V11"
                                stroke="#9CA3AF"
                                strokeWidth="1.5"
                            />
                        </svg>
                        PCI Compliant
                    </span>
                    <span className="w-1 h-1 rounded-full bg-gray-200" />
                    <span>No card data stored</span>
                </div>
            </form>
        </div>
    );
}
