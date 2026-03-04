import { Bell, Search } from "lucide-react";
import React from "react";
import { useLocation } from "react-router-dom";

const pageTitles = {
    "/admin": { title: "Dashboard", sub: "Platform overview & analytics" },
    "/admin/restaurants": {
        title: "Restaurants",
        sub: "Manage restaurant partners",
    },
    "/admin/orders": { title: "Orders", sub: "All orders across the platform" },
    "/admin/users": { title: "Users", sub: "Customers & restaurant owners" },
    "/admin/payouts": { title: "Payouts", sub: "Restaurant payout management" },
    "/admin/settings": { title: "Settings", sub: "Platform configuration" },
};

export default function Topbar() {
    const { pathname } = useLocation();
    const page = pageTitles[pathname] || { title: "Admin", sub: "" };
    return (
        <header>
            <div className="bg-white border-b border-gray-100 px-6 py-3.5 flex items-center gap-4 sticky top-0 z-10">
                <h1 className="text-base font-bold text-gray-900">
                    {page.title}
                </h1>
                <p>{page.sub}</p>
            </div>

            {/* Search */}
            <div className="flex-1 max-w-xs ml-6">
                <div className="relative">
                    <Search
                        size={14}
                        className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400"
                    />
                    <input placeholder="Search..." className="w-full pl-9" />
                </div>
            </div>

            {/* Actions */}
            <div className="ml-auto flex items-center gap-4">
                <button className="relative p-2 rounded-lg hover:bg-gray-100 transition-colors">
                    <Bell size={18} className="text-gray-600" />
                    <span className="absolute top-1.5 right-1.5 w-2 h-2 bg-rose-500 rounded-full" />
                </button>
                <div className="w-8 h-8 rounded-full bg-gradient-to-br from-rose-400 to-rose-600 flex items-center justify-center text-white text-sm font-bold">
                    A
                </div>
            </div>
        </header>
    );
}
