import React, { useState } from "react";
import OrderStats from "./component/OrderStats";
// import StatusBadge from "./component/StatusBadge";
import OrderDrawer from "./component/OrderDrawer";

import { orders } from "../../seeds/admin";

import { Download, Eye, MapPin, RefreshCw, Search, X } from "lucide-react";

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

const STATUS_FILTERS = [
    "all",
    "preparing",
    "in-transit",
    "delivered",
    "cancelled",
];

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
    const [statusFilter, setStatusFilter] = useState("all");
    const [search, setSearch] = useState("");
    const [selected, setSelected] = useState(null);

    const filtered = orders.filter((o) => {
        const matchStatus = statusFilter === "all" || o.status === statusFilter;

        const matchSearch =
            o.id.toLowerCase().includes(search.toLowerCase()) ||
            o.customer.toLowerCase().includes(search.toLowerCase()) ||
            o.restaurant.toLowerCase().includes(search.toLowerCase());

        return matchStatus && matchSearch;
    });

    return (
        <div>
            <OrderStats />

            {/* Toolbar */}
            <div className="flex items-center gap-3 mb-4">
                {/* Status Filter Tabs */}
                <div className="flex items-center gap-1 bg-white border border-gray-200 rounded-xl p-1 shadow-sm">
                    {STATUS_FILTERS.map((f) => {
                        const count =
                            f === "all"
                                ? orders.length
                                : orders.filter((o) => o.status === f).length;

                        return (
                            <button
                                key={f}
                                onClick={() => setStatusFilter(f)}
                                className={`text-xs font-bold px-3 py-1.5 rounded-lg capitalize transition-all whitespace-nowrap ${
                                    statusFilter === f
                                        ? "bg-rose-500 text-white shadow-sm"
                                        : "text-gray-500 hover:text-gray-900"
                                }`}
                            >
                                {f === "in-transit" ? "In Transit" : f}

                                <span
                                    className={`ml-1.5 text-[10px] px-1.5 py-0.5 rounded-full ${
                                        statusFilter === f
                                            ? "bg-white/25 text-white"
                                            : "bg-gray-100 text-gray-500"
                                    }`}
                                >
                                    {count}
                                </span>
                            </button>
                        );
                    })}
                </div>

                {/* Search */}
                <div className="relative max-w-xs flex-1">
                    <Search
                        size={14}
                        className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400"
                    />

                    <input
                        type="text"
                        value={search}
                        placeholder="Search Order ID, customer, restaurant..."
                        onChange={(e) => setSearch(e.target.value)}
                        className="w-full pl-9 pr-4 py-2 text-sm bg-white border border-gray-200 rounded-xl 
            focus:outline-none focus:ring-2 focus:ring-rose-200 focus:border-rose-300 transition-all"
                    />
                </div>

                {/* Export */}
                <button className="flex items-center gap-2 text-sm px-3 py-2 bg-white border border-gray-200 rounded-xl text-gray-600 hover:bg-gray-50 shadow-sm font-medium transition-colors">
                    <Download size={14} />
                    Export
                </button>
            </div>

            {/* Table */}
            <div className="bg-white rounded-2xl border border-gray-100 shadow-sm overflow-hidden">
                <table className="w-full">
                    <thead className="bg-gray-50 border-b border-gray-100">
                        <tr>
                            {[
                                "Order ID",
                                "Customer",
                                "Restaurant",
                                "City",
                                "Items",
                                "Amount",
                                "Status",
                                "Time",
                                "Actions",
                            ].map((h) => (
                                <th
                                    key={h}
                                    className="text-left text-xs font-bold text-gray-500 px-4 py-3.5"
                                >
                                    {h}
                                </th>
                            ))}
                        </tr>
                    </thead>

                    <tbody className="divide-y divide-gray-50">
                        {filtered.length === 0 ? (
                            <tr>
                                <td
                                    colSpan={9}
                                    className="text-center py-6 text-gray-400"
                                >
                                    No orders found
                                </td>
                            </tr>
                        ) : (
                            filtered.map((o) => (
                                <tr
                                    key={o.id}
                                    onClick={() => setSelected(o)}
                                    className="hover:bg-gray-50/60 transition-colors cursor-pointer"
                                >
                                    {/* Order ID */}
                                    <td className="px-4 py-3.5">{o.id}</td>

                                    {/* Customer */}
                                    <td className="px-4 py-3.5">
                                        {o.customer
                                            .split(" ")
                                            .map((n) => n[0])
                                            .join("")}
                                    </td>

                                    {/* Restaurant */}
                                    <td className="px-4 py-3.5">
                                        <div className="flex items-center gap-2">
                                            <div className="w-6 h-6 rounded-lg bg-orange-50 flex items-center justify-center text-orange-400 font-bold text-[10px]">
                                                {o.restaurant[0]}
                                            </div>

                                            <span className="text-sm text-gray-600">
                                                {o.restaurant}
                                            </span>
                                        </div>
                                    </td>

                                    {/* City */}
                                    <td className="px-4 py-3.5">
                                        <span className="flex items-center gap-1 text-xs text-gray-500">
                                            <MapPin
                                                size={11}
                                                className="text-gray-300"
                                            />
                                            {o.city}
                                        </span>
                                    </td>

                                    {/* Items */}
                                    <td className="px-4 py-3.5">
                                        {o.items.length} item
                                        {o.items.length > 1 ? "s" : ""}
                                    </td>

                                    {/* Amount */}
                                    <td className="px-4 py-3.5 font-semibold">
                                        {o.amount}
                                    </td>

                                    {/* Status */}
                                    <td className="px-4 py-3.5">
                                        <StatusBadge status={o.status} />
                                    </td>

                                    {/* Time */}
                                    <td className="px-4 py-3.5 text-xs text-gray-400 whitespace-nowrap">
                                        {o.time}
                                    </td>

                                    {/* Actions */}
                                    <td className="px-4 py-3.5">
                                        <div className="flex items-center gap-1.5">
                                            <button
                                                onClick={(e) => {
                                                    e.stopPropagation();
                                                    setSelected(o);
                                                }}
                                                className="p-1.5 rounded-lg bg-gray-50 hover:bg-blue-50 hover:text-blue-500 transition-colors"
                                            >
                                                <Eye size={13} />
                                            </button>

                                            {(o.status === "preparing" ||
                                                o.status === "in-transit") && (
                                                <button className="p-1.5 rounded-lg bg-red-50 hover:bg-red-100 text-red-500 transition-colors">
                                                    <X size={13} />
                                                </button>
                                            )}

                                            {o.status === "delivered" && (
                                                <button
                                                    title="Issue refund"
                                                    className="p-1.5 rounded-lg bg-blue-50 hover:bg-blue-100 text-blue-500 transition-colors"
                                                >
                                                    <RefreshCw size={13} />
                                                </button>
                                            )}
                                        </div>
                                    </td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>

                {/* Footer */}
                <div className="flex items-center border-t border-gray-50 justify-between px-5 py-3">
                    <span className="text-xs text-gray-400">
                        Showing {filtered.length} of {orders.length} orders
                    </span>

                    <div className="flex items-center gap-1">
                        <button className="w-7 h-7 rounded-lg bg-rose-500 text-white font-bold">
                            1
                        </button>

                        <button className="w-7 h-7 rounded-lg hover:bg-gray-100 text-gray-500 text-xs font-medium">
                            2
                        </button>

                        <button className="w-7 h-7 rounded-lg hover:bg-gray-100 text-gray-500 text-xs font-medium">
                            3
                        </button>
                    </div>
                </div>
            </div>

            <OrderDrawer order={selected} onClose={() => setSelected(null)} />
        </div>
    );
}
