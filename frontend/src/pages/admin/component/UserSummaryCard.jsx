import React from "react";
import { users } from "../../../seeds/admin";
import { ShoppingBag, Users, UserX, UtensilsCrossed } from "lucide-react";

export default function UserSummaryCard() {
    const customers = users.filter((u) => u.role === "CUSTOMER").length;
    const restaurants = users.filter((u) => u.role === "RESTAURANT").length;
    const banned = users.filter((u) => u.status === "banned").length;
    const totalSpent = users
        .filter((u) => u.role === "CUSTOMER")
        .reduce((a, u) => a + u.spent, 0);

    const data = [
        {
            label: "Total Users",
            value: users.length,
            sub: "All roles",
            icon: Users,
            bg: "bg-rose-500",
        },
        {
            label: "Customers",
            value: customers,
            sub: "Active shoppers",
            icon: ShoppingBag,
            bg: "bg-blue-500",
        },
        {
            label: "Restaurant Owners",
            value: restaurants,
            sub: "Platform partners",
            icon: UtensilsCrossed,
            bg: "bg-orange-500",
        },
        {
            label: "Banned Users",
            value: banned,
            sub: "Access revoked",
            icon: UserX,
            bg: "bg-red-500",
        },
    ];

    return (
        <div className="grid grid-cols-4 mb-5">
            {data.map((c) => (
                <div
                    key={c.label}
                    className="bg-white rounded-2xl border-gray-100 shadow-sm p-4 flex items-center gap-4 "
                >
                    <div
                        className={`w-10 h-10 rounded-xl ${c.bg} flex items-center justify-center flex-shrink-0`}
                    >
                        <c.icon size={18} className="text-white" />
                    </div>
                    <div>
                        <div className="text-2xl font-black text-gray-900">
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
