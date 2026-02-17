import React from "react";

export default function StepIndicator({ currentStep }) {
    const steps = ["Cart", "Shipping", "Payment", "Confirmation"];
    return (
        <div>
            {steps.map((label, index) => (
                <div key={index} className="">
                    <div className="">{index + 1}</div>
                    <p>{label}</p>
                </div>
            ))}
        </div>
    );
}
