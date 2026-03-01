import React from "react";
import { motion, AnimatePresence } from "motion/react";
import { Step, StepLabel, Stepper } from "@mui/material";
import { CheckIcon } from "lucide-react";

const STEPS = [
    { id: 0, label: "Address", key: "idle" },
    { id: 1, label: "Payment", key: "pay" },
    { id: 2, label: "Confirmed", key: "confirmed" },
];

// function RoseStepIcon({ active, completed, icon }) {
//     return (
//         <RoseStepIconRoot>
//             {completed ? <CheckIcon className="text-green-500" /> : null}
//         </RoseStepIconRoot>
//     );
// }

export default function CheckoutStepper({ currentIndex }) {
    return (
        <Stepper>
            {STEPS.map((step, i) => (
                <Step key={step.id}>
                    <StepLabel>{step.label}</StepLabel>
                </Step>
            ))}
        </Stepper>
    );
}
