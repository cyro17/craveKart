// import React from "react";
// import StatusBadge from "./StatusBadge";
// import { Mail, MapPin, Phone, RefreshCw, X } from "lucide-react";

import React from "react";
import StatusBadge from "./StatusBadge";

import { X, RefreshCw, Phone, Mail, MapPin } from "lucide-react";

export default function OrderDrawer({ order: o, onClose }) {
    if (!o) return null;

    const subtotal = o.items.reduce((a, i) => a + i.price * i.qty, 0);
    const delivery = o.amount - subtotal > 0 ? o.amount - subtotal : 30;

    return (
        <>
            {/* Overlay */}
            <div
                className="fixed inset-0 bg-black/20 z-40 backdrop-blur-sm"
                onClick={onClose}
            />

            {/* Drawer */}
            <div className="fixed right-0 top-0 h-full w-[420px] bg-white shadow-2xl z-50 flex flex-col">
                {/* Header */}
                <div className="flex items-center justify-between px-5 py-4 border-b border-gray-100">
                    <div>
                        <div className="font-black text-gray-900 text-base">
                            {o.id}
                        </div>
                        <div className="text-xs text-gray-400 mt-0.5">
                            {o.date} • {o.time}
                        </div>
                    </div>

                    <div className="flex items-center gap-2">
                        <StatusBadge status={o.status} />
                        <button
                            onClick={onClose}
                            className="p-1.5 rounded-lg hover:bg-gray-100 transition"
                        >
                            <X size={16} />
                        </button>
                    </div>
                </div>

                {/* Scrollable Content */}
                <div className="flex-1 overflow-y-auto">
                    {/* Customer */}
                    <div className="px-5 py-4 border-b border-gray-50">
                        <div className="text-xs font-bold text-gray-500 uppercase tracking-wider mb-3">
                            Customer
                        </div>

                        <div className="flex items-start gap-3">
                            <div className="w-9 h-9 rounded-full bg-gradient-to-br from-rose-100 to-pink-200 flex items-center justify-center text-rose-600 font-black text-sm flex-shrink-0">
                                {o.customer
                                    .split(" ")
                                    .map((n) => n[0])
                                    .join("")}
                            </div>

                            <div className="flex-1">
                                <div className="text-sm font-bold text-gray-900">
                                    {o.customer}
                                </div>
                                <div className="text-xs text-gray-400 mb-2">
                                    {o.city}
                                </div>

                                <div className="space-y-1.5">
                                    <div className="flex items-center gap-2 text-xs text-gray-600">
                                        <Phone
                                            size={12}
                                            className="text-gray-300"
                                        />
                                        {o.customerPhone}
                                    </div>

                                    <div className="flex items-center gap-2 text-xs text-gray-600">
                                        <Mail
                                            size={12}
                                            className="text-gray-300"
                                        />
                                        {o.customerEmail}
                                    </div>

                                    <div className="flex items-start gap-2 text-xs text-gray-600">
                                        <MapPin size={12} />
                                        {o.address}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    {/* Restaurant */}
                    <div className="px-5 py-4 border-b border-gray-50">
                        <div className="text-xs font-bold text-gray-500 uppercase tracking-wider mb-2">
                            Restaurant
                        </div>

                        <div className="flex items-center gap-3">
                            <div className="w-9 h-9 rounded-xl bg-gradient-to-br from-rose-100 to-orange-100 flex items-center justify-center text-rose-500 font-black text-sm">
                                {o.restaurant[0]}
                            </div>

                            <div>
                                <div className="text-sm font-bold text-gray-900">
                                    {o.restaurant}
                                </div>
                                <div className="text-xs text-gray-400">
                                    {o.restaurantCity}
                                </div>
                            </div>
                        </div>
                    </div>

                    {/* Items */}
                    <div className="px-5 py-4 border-b border-gray-50">
                        <div className="text-xs font-bold text-gray-500 uppercase tracking-wider mb-3">
                            Items Ordered
                        </div>

                        <div className="space-y-2.5">
                            {o.items.map((item, i) => (
                                <div
                                    key={i}
                                    className="flex items-center justify-between"
                                >
                                    <div className="flex items-center gap-2.5">
                                        <span className="w-5 h-5 rounded-full bg-rose-50 text-rose-500 text-[10px] font-black flex items-center justify-center">
                                            {item.qty}
                                        </span>

                                        <span className="text-sm text-gray-800">
                                            {item.name}
                                        </span>
                                    </div>

                                    <span className="text-sm font-semibold text-gray-900">
                                        ₹{item.price * item.qty}
                                    </span>
                                </div>
                            ))}
                        </div>
                    </div>

                    {/* Bill Summary */}
                    <div className="px-5 py-4 border-b border-gray-50 space-y-2">
                        <div className="flex justify-between text-xs text-gray-500">
                            <span>Subtotal</span>
                            <span className="font-semibold text-gray-700">
                                ₹{subtotal}
                            </span>
                        </div>

                        <div className="flex justify-between text-xs text-gray-500">
                            <span>Delivery Fee</span>
                            <span className="font-semibold text-gray-700">
                                ₹{delivery}
                            </span>
                        </div>

                        <div className="flex justify-between text-sm font-bold text-gray-900 pt-1">
                            <span>Total</span>
                            <span className="text-rose-600">₹{o.amount}</span>
                        </div>
                    </div>

                    {/* Footer Actions */}
                    <div className="px-5 py-4 space-y-2">
                        {(o.status === "preparing" ||
                            o.status === "in-transit") && (
                            <button className="w-full bg-red-50 hover:bg-red-100 text-red-600 text-sm font-bold py-2.5 rounded-xl transition-colors flex items-center justify-center gap-2">
                                <X size={15} />
                                Cancel Order
                            </button>
                        )}

                        {o.status === "delivered" && (
                            <button className="w-full bg-blue-50 hover:bg-blue-100 text-blue-600 text-sm font-bold py-2.5 rounded-xl transition-colors flex items-center justify-center gap-2">
                                <RefreshCw size={15} />
                                Issue Refund
                            </button>
                        )}

                        {o.status === "cancelled" && (
                            <div className="text-center text-xs text-gray-400">
                                No actions available for cancelled orders
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </>
    );
}
