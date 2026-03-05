import React from "react";
import { orders } from "../../../seeds/admin";
import {
    Bike,
    CheckCircle,
    Clock,
    IndianRupee,
    ShoppingBag,
    XCircle,
} from "lucide-react";

const fmt = (n) => `₹${n.toLocaleString("en-IN")}`;

export default function OrderStats() {
    const stats = [
        {
            label: "Total Today",
            value: orders.length,
            color: "text-gray-900",
            bg: "bg-gray-50",
            icon: ShoppingBag,
            iconColor: "text-gray-500",
        },
        {
            label: "Delivered",
            value: orders.filter((o) => o.status === "delivered").length,
            color: "text-emerald-700",
            bg: "bg-emerald-50",
            icon: CheckCircle,
            iconColor: "text-emerald-500",
        },
        {
            label: "In Transit",
            value: orders.filter((o) => o.status === "in-transit").length,
            color: "text-blue-700",
            bg: "bg-blue-50",
            icon: Bike,
            iconColor: "text-blue-500",
        },
        {
            label: "Preparing",
            value: orders.filter((o) => o.status === "preparing").length,
            color: "text-amber-700",
            bg: "bg-amber-50",
            icon: Clock,
            iconColor: "text-amber-500",
        },
        {
            label: "Cancelled",
            value: orders.filter((o) => o.status === "cancelled").length,
            color: "text-red-600",
            bg: "bg-red-50",
            icon: XCircle,
            iconColor: "text-red-400",
        },
        {
            label: "Revenue Today",
            value: fmt(
                orders
                    .filter((o) => o.status === "delivered")
                    .reduce((a, o) => a + o.amount, 0)
            ),
            color: "text-rose-600",
            bg: "bg-rose-50",
            icon: IndianRupee,
            iconColor: "text-rose-500",
        },
    ];
    return (
        <div className="grid grid-cols-6 gap-3 mb-5">
            {stats.map((s) => {
                return (
                    <div
                        className={`${s.bg} rounded-2xl p-4 border border-white shadow-sm`}
                    >
                        <div className="flex items-center justify-between mb-2">
                            <s.icon size={15} className={s.iconColor} />
                        </div>
                        <div className={`text-xl font-black ${s.color}`}>
                            {s.value}
                        </div>
                        <div className="text-[10px] font-semibold text-gray-500 mt-0.5">
                            {s.label}
                        </div>
                    </div>
                );
            })}
        </div>
    );
}
