import React from "react";

import {
    LayoutDashboard,
    UtensilsCrossed,
    ShoppingBag,
    Users,
    CreditCard,
    Settings,
    LogOut,
    ChefHat,
    Icon,
} from "lucide-react";

import { NavLink, useNavigate } from "react-router-dom";

const navLinks = [
    { to: "/admin", label: "Dashboard", icon: LayoutDashboard, end: true },
    {
        to: "/admin/restaurants",
        label: "Restaurants",
        icon: UtensilsCrossed,
        badge: 2,
    },
    { to: "/admin/orders", label: "Orders", icon: ShoppingBag },
    { to: "/admin/users", label: "Users", icon: Users },
    { to: "/admin/payouts", label: "Payouts", icon: CreditCard },
    { to: "/admin/settings", label: "Settings", icon: Settings },
];
export default function Sidebar() {
    const navigate = useNavigate();
    return (
        <div>
            {/* Siderbar */}
            <aside className="w-64 h-screen bg-gray-100 border-r border-gray-100 flex flex-col  space-y-5 shrink-0">
                {/* logo */}
                <div className="flex items-center gap-2.5 px4 py-5 border-gray-100">
                    <div className="w-8 h-8 bg-rose-500 flex items-center justify-center rounded-lg">
                        <ChefHat size={17} className="text-white" />
                    </div>
                    <span
                        style={{ fontFamily: "'Mynerve', cursive" }}
                        className="font-bold text-lg text-gray-900 text-lg tracking-wider"
                    >
                        <span>Crave</span>
                        <span className="text-yellow-500">Kart</span>
                    </span>
                    <span className="text-[12px] bg-rose-50 text-rose-500 font-bold px1.5 py-0.5 rounded-md border border-rose-200 tracking-wider">
                        ADMIN
                    </span>
                </div>

                <nav className="flex-1 px-3 py-4 space-y-0.5">
                    {/* Nav Links */}
                    {navLinks.map(({ to, label, icon: Icon, badge }) => (
                        <NavLink
                            key={to}
                            to={to}
                            end={to === "/admin"}
                            className={({ isActive }) =>
                                `flex items-center gap-3 px-4 py-2 rounded-lg transition-colors font-medium
                                    ${
                                        isActive
                                            ? "bg-rose-50 text-rose-600"
                                            : "text-gray-600 hover:bg-gray-100 hover:text-gray-900"
                                    }`
                            }
                        >
                            {({ isActive }) => (
                                <>
                                    <Icon
                                        size={20}
                                        strokeWidth={isActive ? 2.5 : 2}
                                    />
                                    {label}
                                    {badge && (
                                        <span className="ml-auto text-[10px] bg-amber-100 text-amber-700 font-bold px-1.5 py-0.5 rounded-full ">
                                            {badge}
                                        </span>
                                    )}
                                </>
                            )}
                        </NavLink>
                    ))}
                </nav>

                {/* Admin Profile +  Logout */}
                <div className="px-3 py-4 border-t border-gray-100 ">
                    <div className="flex items-center gap-3 px-3 py-2.5 rounded-xl hover:bg-gray-50 cursor-pointer group">
                        <div>A</div>
                    </div>
                    <div className="flex-1 min-w-0">
                        <div className="text-sm font-semibold text-gray-900 truncate">
                            Super Admin
                        </div>
                        <div className="text-xs text-gray-400 truncate">
                            admin@cravekart.com
                        </div>
                    </div>
                    <button title="Logout">
                        <LogOut
                            size={25}
                            className="text-gray-500 group-hover:text-red-500 transition-colors"
                        />
                    </button>
                </div>
            </aside>
        </div>
    );
}
