import { CheckCircle, Clock, TrendingUp, UtensilsCrossed } from "lucide-react";
import React from "react";

const fmt = (n) => `₹${n.toLocaleString("en-IN")}`;

export default function SummaryCards({ data }) {
    const active = data.filter((r) => r.status === "active").length;
    const pending = data.filter((r) => r.status === "pending").length;
    const suspended = data.filter((r) => r.status === "suspended").length;
    const totalRev = data.reduce((a, r) => a + r.revenue, 0);

    const cards = [
        {
            label: "Total Restaurants",
            value: data.length,
            icon: UtensilsCrossed,
            color: "bg-rose-500",
            sub: "All partners",
        },
        {
            label: "Active",
            value: active,
            icon: CheckCircle,
            color: "bg-emerald-500",
            sub: "Accepting orders",
        },
        {
            label: "Pending Approval",
            value: pending,
            icon: Clock,
            color: "bg-amber-500",
            sub: "Awaiting review",
        },
        {
            label: "Total Revenue",
            value: fmt(totalRev),
            icon: TrendingUp,
            color: "bg-blue-500",
            sub: "All time",
        },
    ];
    return (
        <div className="grid grid-cols-4 gap-4 mb-5">
            {cards.map((c, i) => (
                <div
                    key={c.label}
                    className="bg-white rounded-2xl border border-gray-100 shadow-sm p-4 flex items-center gap-4"
                >
                    <div
                        className={`w-10 h-10 rounded-xl ${c.color} flex items-center justify-center flex-shrink-0`}
                    >
                        <c.icon size={18} className="text-white" />
                    </div>
                    <div>
                        <div className="text-xl font-black text-gray-900">
                            {c.value}
                        </div>
                        <div className="text-xs font-semibold text-gray-500">
                            {c.label}
                        </div>
                        <div className="text-[10px] text-gray-400">{c.sub}</div>
                    </div>
                </div>
            ))}
        </div>
    );
}
