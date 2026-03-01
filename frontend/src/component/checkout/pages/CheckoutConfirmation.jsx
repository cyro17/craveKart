import React from "react";
import { motion } from "motion/react";
import CheckIcon from "@mui/icons-material/Check";
import { resetOrder } from "../../../State/order/orderSlice";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";

export default function CheckoutConfirmation({ orderId }) {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [countdown, setCountdown] = React.useState(5);
    useEffect(() => {
        const interval = setInterval((prev) => {
            if (prev <= 1) {
                clearInterval(interval);
                dispatch(resetOrder());
                navigate("/profile/orders");
            }
            return prev - 1;
        }, 1000);

        return () => clearInterval(interval);
    }, []);

    const handleNavigate = (path) => {
        dispatch(resetOrder());
        navigate(path);
    };

    return (
        <div>
            CheckoutConfirmation
            <motion.div>
                <CheckIcon sx={{ color: "#22C55E", fontSize: 40 }} />
            </motion.div>
            <motion.div>
                {orderId && (
                    <div className="inline-flex font-bold text-gray-900 mb-2">
                        <span>Order ID</span>
                        <span>#{orderId}</span>
                    </div>
                )}

                {/* steps */}
                <div className="flex justify-center gap-6 my-8">
                    {[
                        { icon: "✅", label: "Order Confirmed" },
                        { icon: "👨‍🍳", label: "Being Prepared" },
                        { icon: "🛵", label: "Out for Delivery" },
                    ].map((step, index) => (
                        <motion.div
                            key={index}
                            initial={{ opacity: 0, y: 12 }}
                            animate={{ opacity: 1, y: 0 }}
                            transition={{ duration: 0.6 + index * 0.1 }}
                        >
                            <div
                                className={`w-12 h-12 rounded-full flex items-center justify-center text-xl 
                                        ${
                                            index === 0
                                                ? "bg-rose-50"
                                                : "bg-gray-50"
                                        }
                                    `}
                            >
                                {step.icon}
                            </div>
                            <span
                                className={`text-xs font-medium ${
                                    index === 0
                                        ? "text-rose-500 "
                                        : "text-gray-500"
                                } `}
                            >
                                {step.label}
                            </span>
                            {index < 2 && (
                                <div className="absolute translate-x-16 translate-y-[-28px] hidden md:block">
                                    <div className="w-8 h-0.5 bg-gray" />
                                </div>
                            )}
                        </motion.div>
                    ))}
                </div>
            </motion.div>
            {/* // CTA Buttons */}
            <div className="flex gap-3 justify-center">
                <button
                    onClick={() => {
                        handleNavigate("/profile/orders");
                    }}
                >
                    Track My Order
                </button>
                <button
                    onClick={() => {
                        // dispatch(resetOrder());
                        handleNavigate("/");
                    }}
                    className="px-6 py-2.5 border-2 border-gray-200 text-gray-600 hover:bg-gray-50 rounded-xl font-medium text-sm transition-colors"
                >
                    Back to Home
                </button>
            </div>
        </div>
    );
}
