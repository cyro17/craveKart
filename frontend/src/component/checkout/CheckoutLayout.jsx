import React from "react";
import { useDispatch, useSelector } from "react-redux";
import CheckoutStepper from "./components/CheckoutStepper";
import { motion } from "motion/react";
import CheckoutShipping from "./pages/CheckoutShipping";
import { Elements } from "@stripe/react-stripe-js";
import { loadStripe } from "@stripe/stripe-js";
import CheckoutPayment from "./pages/CheckoutPayment";
import {
    setPaymentConfirmed,
    setPaymentFailed,
} from "../../State/order/orderSlice";
import CheckoutConfirmation from "./pages/CheckoutConfirmation";
import CartSummary from "./components/CartSummary";

const stripePromise = loadStripe(
    "pk_test_51PfRxrRxag7j52K6XwHgf1uF21HRiDpp9EsC2DyfSx6UTmWUjP6fL3zOcQKJo6ch1E74M1jf32JzWQf1VtmmwpRj004Q7UBXbG"
);

function getStepIndex(paymentStep) {
    if (paymentStep === "idle" || paymentStep === "placing") return 0;
    if (paymentStep === "pay" || paymentStep === "processing") return 1;
    if (paymentStep === "confirmed") return 2;
    if (paymentStep === "failed") return 1;
    return 0;
}

const motionProps = {
    initial: { opacity: 0, y: 16 },
    animate: { opacity: 1, y: 0 },
    exit: { opacity: 0, y: -16 },
    transition: { duration: 0.25 },
};
export default function CheckoutLayout() {
    const dispatch = useDispatch();
    const { paymentStep, clientSecret, orderId, error } = useSelector(
        (state) => state.order
    );
    const stepIndex = getStepIndex(paymentStep);

    const isPaymentStep =
        paymentStep === "pay" ||
        paymentStep === "processing" ||
        paymentStep === "failed";

    return (
        <div className="min-h-screen bg-gray-50">
            {/* Sticky header */}
            <div className="bg-white border-b border-gray-100 px-6 py-4 sticky top-0 z-30 shadow-sm">
                <div className="max-w-6xl mx-auto flex items-center justify-between">
                    <CheckoutStepper currentIndex={stepIndex} />
                    <div className="w-28" />
                </div>
            </div>

            {/* Content */}
            <div className="max-w-6xl mx-auto px-4 py-8 grid grid-cols-1 lg:grid-cols-3 gap-6 items-start">
                <div className="lg:col-span-2 space-y-4">
                    {/* Step 1 — Shipping */}
                    {(paymentStep === "idle" || paymentStep === "placing") && (
                        <motion.div key="shipping" {...motionProps}>
                            <CheckoutShipping />
                            {paymentStep === "placing" && (
                                <p className="mt-4 text-sm text-gray-500 animate-pulse">
                                    Placing your order...
                                </p>
                            )}
                        </motion.div>
                    )}

                    {/* Step 2 — Payment: Elements stays mounted as long as clientSecret exists */}
                    {clientSecret && (
                        <Elements
                            stripe={stripePromise}
                            options={{ clientSecret }}
                            key={clientSecret} // re-mount only if secret changes
                        >
                            <div
                                style={{
                                    display: isPaymentStep ? "block" : "none",
                                }}
                            >
                                <CheckoutPayment
                                    orderId={orderId}
                                    onSuccess={() =>
                                        dispatch(setPaymentConfirmed())
                                    }
                                    onFailure={(msg) =>
                                        dispatch(setPaymentFailed(msg))
                                    }
                                />
                                {paymentStep === "failed" && error && (
                                    <p className="mt-3 text-sm text-red-500">
                                        {error}
                                    </p>
                                )}
                            </div>
                        </Elements>
                    )}

                    {/* Step 3 — Confirmed */}
                    {paymentStep === "confirmed" && (
                        <motion.div key="confirmed" {...motionProps}>
                            <CheckoutConfirmation orderId={orderId} />
                        </motion.div>
                    )}
                </div>

                {paymentStep !== "confirmed" && (
                    <div className="sticky top-24">
                        <CartSummary />
                    </div>
                )}
            </div>
        </div>
    );
}
