import React, { useEffect, useState } from "react";
import { Outlet, useLocation, useNavigate } from "react-router-dom";
import StepIndicator from "./components/StepIndicator";
import { useSelector } from "react-redux";
import CheckoutSummary from "./pages/CheckoutSummary";
import CartSummary from "./components/CartSummary";
import CheckoutShipping from "./pages/CheckoutShipping";
import CheckoutPayment from "./pages/CheckoutPayment";
import CheckoutConfirmation from "./pages/CheckoutConfirmation";
import PaymentButton from "./components/PaymentButton";

const steps = ["Shipping", "Payment", "Summary", "Confirmation"];

export default function CheckoutLayout() {
    const [currentStep, setCurrentStep] = useState(0);

    const next = () => setCurrentStep((prev) => prev + 1);
    const back = () => setCurrentStep((prev) => prev - 1);

    const renderStep = () => {
        switch (currentStep) {
            case 0:
                return <CheckoutShipping next={next} />;
            case 1:
                return <CheckoutPayment next={next} back={back} />;
            case 2:
                return <CheckoutSummary next={next} back={back} />;
            case 3:
                return <CheckoutConfirmation next={next} />;
            default:
                return null;
        }
    };

    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState();
    const handlePaymentCheckout = async () => {};

    return (
        // main content
        <div className=" max-w-6xl mx-auto px-4 py-10 grid grid-cols-1 lg:grid-cols-3 gap-8 min-h-screen">
            {/* left section */}
            <div className=" flex flex-col py-4 shadow-sm lg:col-span-2 space-y-6">
                <CheckoutShipping />
                <CheckoutPayment />
                <PaymentButton className="" />
            </div>
            {/* right side */}
            <div className="sticky top-24">
                <CartSummary />
            </div>
        </div>
    );
}
