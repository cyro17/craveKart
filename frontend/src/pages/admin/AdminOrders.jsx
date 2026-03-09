import React, { useState } from "react";
import OrderStats from "./component/OrderStats";
import { orders } from "../../seeds/admin";

import {
    Download,
    Eye,
    MapPin,
    RefreshCw,
    Search,
    Table,
    X,
} from "lucide-react";
import StatusBadge from "./component/StatusBadge";
import OrderDrawer from "./component/OrderDrawer";

=======
import { Download, Eye, RefreshCcw, RefreshCw, Search, X } from "lucide-react";
import OrderDrawer from "./component/OrderDrawer";

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

=======

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

            {/* ToolBar */}
            <div>
            {/* Toolbar */}
            <div className="flex items-center gap-3 mb-4">
                {/* Status filter tabs */}
                <div className="flex items-center gap-1 bg-white border border-gray-200 rounded-xl p-1 shadow-sm">
                    {STATUS_FILTERS.map((f) => {
                        const count =
                            f === "all"
                                ? orders?.length
                                : orders?.filter((o) => o.status === f).length;

                        return (
                            <button
                                className={`text-xs font-bold px-3 py-1.5  rounded-lg capitalize transition-all whitespace-nowrap 
                                ? orders.length
                                : orders.filter((o) => o.status === f).length;
                        return (
                            <button
                                key={f}
                                onClick={() => setStatusFilter(f)}
                                className={`text-xs font-bold px-3 py-1.5 rounded-lg capitalize transition-all whitespace-normal *:first-letter:
                                ${
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
                                    } ? "bg-white/25 text-white": "bg-gray-100 text-gray-500"`}
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
                        className="absolute left-3 top-1/2 -translate-y-1/2"
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
                <div>
                    <button className="flex items-center gap-2 text-sm px3 py-2 bg-white border border-gray-200 rounded-xl text-gray-600 hover:bg-gray-50 shadow-sm font-medium transition-colors">
                        <Download size={14} />
                        Export
                    </button>
                </div>
            </div>

                        className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400"
                    />

                    <input
                        type="text"
                        value={search}
                        onChange={(e) => setSearch(e.target.value)}
                        placeholder="Search"
                        className="w-full pl-9 pr-4 py-2 text-sm bg-white border border-gray-200 rounded-xl transition-all shadow-sm"
                    />
                </div>
                {/* Download Button */}
                <div
                    className="flex items-center gap-2 text-sm px-3 py-2 items bg-white border border-gray-200 rounded-xl text-gray-600
                hover:bg-gray-50 shadow-sm font-medium transition-colors"
                >
                    <Download size={14} /> Export
                </div>
            </div>

            {/* Table */}
            <div className="bg-white rounded-2xl border border-gray-100 shadow-sm overflow-hidden">
                <table className="w-full">
                    <thead className="bg-gray-50 border-b border-gray-100">
                        <tr>
                            {[
                                "Order Id",
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
                    <tbody className="">
                        {filtered.map((o) => (
                            <tr className="">
                                {/* Order ID */}
                                <td>
                                    <span>{o.id}</span>
                                </td>
                                {/* Customer */}
                                <td className="px-4 py-3.5">
                                    <div className="flex items-center ">
                                        <div className="">
                                            {o.customer
                                                .split(" ")
                                                .map((n) => n[0])}
                                        </div>
                                    </div>
                                </td>
                                {/* Restaurant */}
                                <td className="px-4 py-3.5">
                                    <div className="flex items-center">
                                        <div className="w-6 h-6 rounded-lg bg-orange-50 flex items-center justify-center text-orange-400 font-black text-[10px] flex-shrink-0">
                                            {o.restaurant[0]}
                                        </div>
                                        <span className="text-sm text-gray-600">
                                            {o.restaurant}
                                        </span>
                                    </div>
                                </td>
                                {/* City */}
                                <td className="px-0 py-3.5">
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
                                    <span className="text-xs font-bold text-gray-700 bg-gray-100 px-2 rounded-lg">
                                        {o.items.length} item
                                        {o.items.length > 1 ? "s" : ""}
                                    </span>
                                </td>
                                {/* Amount */}
                                <td className="px-4 py-3.5">
                                    <span className="text-sm font-black text-gray-900">
                                        {o.amount}
                                    </span>
                                </td>
                                {/* Status */}
                                <td className="px-4 py-3.5">
                                    <div>
                                        <button>
                                            <StatusBadge status={o.status} />
                                        </button>
                                    </div>
                                </td>
                                {/* Time */}
                                <td className="px-4 py-3.5 text-xs text-gray-400 whitespace-nowrap">
                                    {o.time}
                                </td>
                                {/* Actions */}
                                <td className="px-4 py-3.5">
                                    <div className="flex items-center gap-1.5">
                                        <button>
                                            <Eye size={13} />
                                        </button>
                                        {(o.status === "preparing" ||
                                            o.status === "in-transit") && (
                                            <button>
                                                <X size={13} />
                                            </button>
                                        )}

                                        {o.status === "delivered" && (
                                            <button title="Issue refund">
                                                <RefreshCw size={13} />
                                            </button>
                                        )}
                                    </div>
                                </td>
                            </tr>
                        ))}
                    <tbody className="divide-y divide-gray-50">
                        {filtered.length === 0 ? (
                            <tr>
                                <td colSpan={9}>No orders found</td>
                            </tr>
                        ) : (
                            filtered.map((o) => (
                                <tr
                                    key={o.id}
                                    onClick={() => setSelected(o)}
                                    colSpan={9}
                                    className="hover:bg-gray-50/60 transition-colors cursor-pointer"
                                >
                                    {/* order id */}
                                    <td className="px-4 py-3.5">
                                        <span>{o.id}</span>
                                    </td>
                                    {/* customer */}
                                    <td className="px-4 py-3.5">
                                        <div>
                                            {o.customer
                                                .split(" ")
                                                .map((n) => n[0])
                                                .join("")}
                                        </div>
                                    </td>
                                    {/* restaurant */}
                                    <td className="px-4 py-3.5">
                                        {o.restaurant}
                                    </td>

                                    {/* city */}
                                    <td className="px-4 py-3.5">{o.city}</td>

                                    {/* items */}
                                    <td className="px-4 py-3.5">
                                        {o.items.length} item{" "}
                                        {o.length > 1 ? "s" : ""}
                                    </td>

                                    {/* amount */}
                                    <td className="px-4 py-3.5">
                                        <span>{o.amount}</span>
                                    </td>

                                    {/* Status */}
                                    <td className="px-4 py-3.5">
                                        <StatusBadge status={o.status} />
                                    </td>

                                    {/* Time */}
                                    <td className="px-4 py-3.5">{o.time}</td>

                                    {/* Actions */}
                                    <td className="px-4 py-3.5">
                                        <div className="flex items-center gap-1.5">
                                            <button
                                                onClick={() => setSelected(o)}
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
                <div className="px-5 py-3 border-gray-50 flex items-center justify-between">
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
                <div className="flex items-center border-t border-gray-50 justify-between px-5 py-3">
                    <span>
                        showing {filtered.length} of {orders.length} orders
                    </span>
                    <div className="flex items-center gap-1">
                        <button>1</button>
                        <button>2</button>
                        <button>3</button>
                    </div>
                </div>
            </div>
            <OrderDrawer order={selected} onClose={() => setSelected(null)} />
        </div>
    );
}
