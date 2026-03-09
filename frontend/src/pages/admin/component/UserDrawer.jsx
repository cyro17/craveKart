import React from "react";
import { fmt, getInitials } from "../../../seeds/admin";

import { Ban, Calendar, Mail, MapPin, Phone, RefreshCw, X } from "lucide-react";

const avatarColors = [
    "from-rose-200 to-rose-400",
    "from-orange-200 to-orange-400",
    "from-amber-200 to-amber-400",
    "from-emerald-200 to-emerald-400",
    "from-blue-200 to-blue-400",
    "from-violet-200 to-violet-400",
];

function RoleBadge({ role }) {
    return (
        <span
            className={`text-xs font-bold px-2.5 py-1 rounded-full
        ${
            role === "CUSTOMER"
                ? "bg-blue-50 text-blue-700"
                : "bg-orange-50 text-orange-700"
        }`}
        >
            {role === "CUSTOMER" ? "Customer" : "Restaurant"}
        </span>
    );
}

// ── Badges ────────────────────────────────────────────────────────────────────

function StatusBadge({ status }) {
    return (
        <span
            className={`inline-flex items-center gap-1.5 text-xs font-semibold px-2.5 py-1 rounded-full border capitalize
        ${
            status === "active"
                ? "bg-emerald-50 text-emerald-700 border-emerald-200"
                : "bg-red-50 text-red-600 border-red-200"
        }`}
        >
            <span
                className={`inline-block w-1.5 h-1.5 rounded-full ${
                    status === "active" ? "bg-emerald-500" : "bg-red-500"
                }`}
            />
            {status}
        </span>
    );
}

export default function UserDrawer({ user: u, onClose, onToggleBan }) {
    const colorIdx = u?.id % avatarColors.length;
    if (!u) return null;
    const recentOrders = [
        {
            id: "#CK-8821",
            restaurant: "Biryani Blues",
            amount: 640,
            status: "delivered",
        },
        {
            id: "#CK-8816",
            restaurant: "Biryani Blues",
            amount: 1200,
            status: "delivered",
        },
        {
            id: "#CK-8817",
            restaurant: "Wok This Way",
            amount: 350,
            status: "cancelled",
        },
    ];

    return (
        <div>
            <div className="fixed inset-0 bg-black/20 z-40" onClick={onClose} />
            <div className="fixed right-0 top-0 h-full w-[400px] bg-white shadow-2xl z-50 flex flex-col">
                {/* Header */}
                <div className="px-5 py-5 border-b border-gray-100">
                    <div className="flex items-start justify-between mb-4">
                        <div className="flex items-center gap-3">
                            {/* name initials */}
                            <div
                                className={`w-12 h-12 rounded-2xl bg-gradient-to-br ${avatarColors[colorIdx]} 
                                    flex items-center justify-center text-white font-black text-base`}
                            >
                                {getInitials(u?.name)}
                            </div>
                            {/* name */}
                            <div className=" font-black text-gray-900">
                                {u.name}
                            </div>

                            {/* role and status */}
                            <div className="flex items-center gap-2 mt-1">
                                <RoleBadge role={u.role} />
                                <StatusBadge status={u.status} />
                            </div>

                            <button className="p-1.5 rounded-lg hover:bg-gray-100 transition-colors">
                                <X size={16} className="text-gray-500" />
                            </button>
                        </div>
                    </div>
                </div>

                {/* Body */}
                <div className="flex-1 overflow-y-auto">
                    {/* Contact */}
                    <div className="px-5 border-b border-gray-50">
                        <div className="text-xs font-bold text-gray-400 uppercase tracking-wider mb-3">
                            <div className="space-y-2.5">
                                {[
                                    { icon: Mail, value: u.email },
                                    { icon: Phone, value: u.phone },
                                    { icon: MapPin, value: u.city },
                                    {
                                        icon: Calendar,
                                        value: `Joined ${u.joined}`,
                                    },
                                ].map(({ icon: Icon, value }) => (
                                    <div
                                        className="flex items-center gap-2.5 text-sm text-gray-600 "
                                        key={value}
                                    >
                                        <div className="w-7 h-7 rounded-lg bg-gray-50 border border-gray-100 flex items-center justify-center flex-shrink-0">
                                            <Icon
                                                size={12}
                                                className="text-gray-400"
                                            />
                                        </div>
                                        {value}
                                    </div>
                                ))}
                            </div>
                        </div>
                    </div>

                    {/* Stats */}
                    <div className="px-5 py-4 border-b border-gray-50">
                        <div className="text-xs font-bold text-gray-400 uppercase tracking-wider">
                            Activity
                        </div>
                        <div className="grid grid-cols-2 gap-3">
                            {[
                                {
                                    label:
                                        u.role === "CUSTOMER"
                                            ? "Total Orders"
                                            : "Orders Received",
                                    value: u.orders.toLocaleString(),
                                },
                                {
                                    label:
                                        u.role === "CUSTOMER"
                                            ? "Total Spent"
                                            : "Total Revenue",
                                    value: u.spent ? fmt(u.spent) : "—",
                                },
                                { label: "Last Active", value: u.lastActive },
                                { label: "Member Since", value: u.joined },
                            ].map((s) => (
                                <div
                                    key={s.label}
                                    className="bg-gray-50 rounded-xl p-3"
                                >
                                    <div className="text-[10px] text-gray-400 font-medium">
                                        {s.label}
                                    </div>
                                    <div className="text-sm font-black text-gray-900 mt-0.5">
                                        {s.value}
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>

                    {/* Recent Orders */}
                    {u.role === "CUSTOMER" && (
                        <div className="px-5 py-4">
                            <div className="text-xs font-bold text-gray-400 uppercase tracking-wider">
                                Recent Orders
                            </div>
                            <div className="space-y-2">
                                {recentOrders.map((o) => (
                                    <div
                                        key={o.id}
                                        className="flex items-center justify-between py-2 border-b border-gray-50 last:border-0"
                                    >
                                        <div>
                                            <div className="text-xs font-mono font-bold text-gray-900">
                                                {o.id}
                                            </div>
                                            <div className="text-[10px] text-gray-400">
                                                {o.restaurant}
                                            </div>
                                        </div>
                                        <div className="text-right">
                                            <div
                                                className="text-xs font-bold font-mono  text-gray-900 
                                                "
                                            >
                                                {o.amount}
                                            </div>
                                            <span
                                                className={`text-[9px] font-bold px-1.5 py-0.5 rounded-full 
                                                    ${
                                                        o.status === "delivered"
                                                            ? "bg-emerald-50 text-emerald-700"
                                                            : "bg-gray-100 text-gray-500"
                                                    }}`}
                                            >
                                                {o.status}
                                            </span>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        </div>
                    )}
                </div>

                {/* Footer */}
                <div className="px-5 py-4 border-t border-gray-100 space-y-2">
                    {u.status === "active" ? (
                        <button className="w-full bg-red-50 hover:bg-red-100 text-red-700 text-sm font-bold py-2.5 rounded-xl transition-colors flex items-center justify-center gap-2">
                            <Ban size={15} />
                            Ban User
                        </button>
                    ) : (
                        <button className="w-full bg-emerald-50 hover:bg-emerald-100 text-emerald-700 text-sm font-bold py-2.5 rounded-xl transition-colors flex items-center justify-center gap-2">
                            Unban User
                        </button>
                    )}
                    <button
                        className={`w-full bg-gray-50 hover:bg-gray-100 text-gray-600 text-sm py-2.5 rounded-xl transition-colors flex items-center justify-center gap-2`}
                    >
                        <RefreshCw size={15} /> Reset Password
                    </button>
                </div>
            </div>
        </div>
    );
}
