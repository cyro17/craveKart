import React from "react";
import OrderStats from "./component/OrderStats";

const statusConfig = {
    delivered: {
        label: "Delivered",
        cls: "bg-emerald-50 text-emerald-700 border-emerald-200",
        dot: "bg-emerald-500",
    },
    "in-transit": {
        label: "In Transit",
        cls: "bg-blue-50 text-blue-700 border-blue-200",
        dot: "bg-blue-500",
    },
    preparing: {
        label: "Preparing",
        cls: "bg-amber-50 text-amber-700 border-amber-200",
        dot: "bg-amber-500",
    },
    cancelled: {
        label: "Cancelled",
        cls: "bg-gray-100 text-gray-500 border-gray-200",
        dot: "bg-gray-400",
    },
};

function StatusBadge({ status }) {
    const cfg = statusConfig[status] || {
        label: status,
        cls: "bg-gray-100 text-gray-500 border-gray-200",
        dot: "bg-gray-400",
    };
    return (
        <span
            className={`inline-flex items-center gap-1.5 text-xs font-semibold px-2.5 py-1 rounded-full border ${cfg.cls}`}
        >
            <span
                className={`inline-block w-1.5 h-1.5 rounded-full ${cfg.dot}`}
            />
            {cfg.label}
        </span>
    );
}

export default function AdminOrders() {
    return (
        <div>
            <OrderStats />
        </div>
    );
}
