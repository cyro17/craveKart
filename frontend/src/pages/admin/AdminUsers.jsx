import React, { useState } from "react";
import { users } from "../../seeds/admin";
import { Ban, Check, Download, Eye, MapPin, Search } from "lucide-react";
import UserDrawer from "./component/UserDrawer";
import UserSummaryCard from "./component/UserSummaryCard";

const ROLE_FILTERS = ["all", "CUSTOMER", "RESTAURANT"];
const STATUS_FILTERS = ["all", "active", "banned"];

const fmt = (n) => `₹${n.toLocaleString("en-IN")}`;
const getInitials = (name) =>
    name
        .split(" ")
        .map((n) => n[0])
        .join("")
        .toUpperCase();

const avatarColors = [
    "from-rose-200 to-rose-400",
    "from-orange-200 to-orange-400",
    "from-amber-200 to-amber-400",
    "from-emerald-200 to-emerald-400",
    "from-blue-200 to-blue-400",
    "from-violet-200 to-violet-400",
];

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

function RoleBadge({ role }) {
    return (
        <span
            className={`text-xs font-bold px-2.5 py-1 rounded-full ${
                role === "CUSTOMER"
                    ? "bg-blue-50 text-blue-700"
                    : "bg-orange-50 text-orange-700"
            }`}
        >
            {role === "CUSTOMER" ? "Customer" : "Restaurant"}
        </span>
    );
}

function SummaryCards() {
    const customers = users.filter((u) => u.role === "CUSTOMER").length;
    const restaurants = users.filter((u) => u.role === "RESTAURANT").length;
    const banned = users.filter((u) => u.role === "banned").length;
    const totalSpent = users
        .filter((u) => u.role === "CUSTOMER")
        .reduce((a, u) => a + u.spent);
}

export default function AdminUsers() {
    const [userList, setUserList] = useState(users);
    const [roleFilter, setRoleFilter] = useState("all");
    const [statusFilter, setStatusFilter] = useState("all");
    const [search, setSearch] = useState("");
    const [selected, setSelected] = useState(null);

    const filtered = userList.filter((u) => {
        const matchRole = roleFilter === "all" || u.role === roleFilter;
        const matchStatus = statusFilter === "all" || u.status === statusFilter;
        const matchSearch =
            u.name.toLowerCase().includes(search.toLowerCase()) ||
            u.email.toLowerCase().includes(search.toLowerCase()) ||
            u.city.toLowerCase().includes(search.toLowerCase());

        return matchRole && matchStatus && matchSearch;
    });

    const handleToggleBan = (id) => {
        setUserList((prev) =>
            prev.map((u) =>
                u.id === id
                    ? {
                          ...u,
                          status: u.status === "active" ? "banned" : "active",
                      }
                    : u
            )
        );

        setSelected((prev) =>
            prev
                ? {
                      ...prev,
                      status: prev.status === "active" ? "banned" : "active",
                  }
                : null
        );
    };

    return (
        <div style={{ fontFamily: "'DM Sans', system-ui, sans-serif" }}>
            <style>{`@import url('https://fonts.googleapis.com/css2?family=DM+Sans:wght@400;500;600;700;900&display=swap');`}</style>

            <UserSummaryCard />

            {/* Toolbar */}
            <div className="flex items-center gap-3 mb-4 flex-wrap">
                {/* Role Tabs */}
                <div className="flex items-center gap-1 bg-white border border-gray-200 rounded-xl p-1 shadow-sm">
                    {ROLE_FILTERS.map((f) => {
                        const count =
                            f === "all"
                                ? userList.length
                                : userList.filter((u) => u.role === f).length;
                        const label =
                            f === "all"
                                ? "All"
                                : f === "CUSTOMER"
                                ? "Customers"
                                : "Restaurants";
                        return (
                            <button
                                key={f}
                                onClick={() => setRoleFilter(f)}
                                className={`text-xs font-bold px-3 py-1.5 rounded-lg transition-all
                ${
                    roleFilter === f
                        ? "bg-rose-500 text-white shadow-sm"
                        : "text-gray-500 hover:text-gray-900"
                }`}
                            >
                                {label}
                                <span
                                    className={`ml-1.5 text-[10px] px-1.5 py-0.5 rounded-full
                ${
                    roleFilter === f
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

                {/* Status Tabs */}
                <div className="flex items-center gap-1 bg-white border border-gray-200 rounded-xl p-1 shadow-sm">
                    {STATUS_FILTERS.map((f) => {
                        const count =
                            f === "all"
                                ? userList.length
                                : userList.filter((u) => u.status === f).length;
                        return (
                            <button
                                key={f}
                                onClick={() => setStatusFilter(f)}
                                className={`text-xs font-bold px-3 py-1.5 rounded-lg capitalize transition-all
                ${
                    statusFilter === f
                        ? "bg-rose-500 text-white shadow-sm"
                        : "text-gray-500 hover:text-gray-900"
                }`}
                            >
                                {f}
                                <span
                                    className={`ml-1.5 text-[10px] px-1.5 py-0.5 rounded-full
                ${
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
                        value={search}
                        onChange={(e) => setSearch(e.target.value)}
                        placeholder="Search name, email, city..."
                        className="w-full pl-9 pr-4 py-2 text-sm bg-white border border-gray-200 rounded-xl
            focus:outline-none focus:ring-2 focus:ring-rose-200 focus:border-rose-300 transition-all shadow-sm"
                    />
                </div>

                <div className="ml-auto flex items-center gap-2">
                    <button className="flex items-center gap-2 text-sm px-3 py-2 bg-white border border-gray-200 rounded-xl text-gray-600 hover:bg-gray-50 shadow-sm font-medium transition-colors">
                        <Download size={14} /> Export
                    </button>
                </div>
            </div>

            {/* Table */}
            <div className="bg-white rounded-2xl border border-gray-100 shadow-sm overflow-hidden">
                <table className="w-full">
                    <thead className="bg-gray-50 border-b border-gray-100">
                        <tr>
                            {[
                                "User",
                                "Email",
                                "Role",
                                "City",
                                "Orders",
                                "Total Spent / Revenue",
                                "Last Active",
                                "Joined",
                                "Status",
                                "Actions",
                            ].map((h) => (
                                <th
                                    key={h}
                                    className="text-left text-xs font-bold text-gray-500 px-4 py-3.5 whitespace-nowrap"
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
                                    colSpan={10}
                                    className="text-center py-12 text-sm text-gray-400"
                                >
                                    No users found
                                </td>
                            </tr>
                        ) : (
                            filtered.map((u) => {
                                const colorIdx = u.id % avatarColors.length;
                                return (
                                    <tr
                                        key={u.id}
                                        className="hover:bg-gray-50/60 transition-colors cursor-pointer"
                                        onClick={() => setSelected(u)}
                                    >
                                        {/* Avatar + Name */}
                                        <td className="px-4 py-3.5">
                                            <div className="flex items-center gap-3">
                                                <div
                                                    className={`w-8 h-8 rounded-full bg-gradient-to-br ${avatarColors[colorIdx]} flex items-center justify-center text-white font-black text-xs flex-shrink-0`}
                                                >
                                                    {getInitials(u.name)}
                                                </div>
                                                <span className="text-sm font-bold text-gray-900 whitespace-nowrap">
                                                    {u.name}
                                                </span>
                                            </div>
                                        </td>

                                        <td className="px-4 py-3.5 text-sm text-gray-500">
                                            {u.email}
                                        </td>

                                        <td
                                            className="px-4 py-3.5"
                                            onClick={(e) => e.stopPropagation()}
                                        >
                                            <RoleBadge role={u.role} />
                                        </td>

                                        <td className="px-4 py-3.5">
                                            <span className="flex items-center gap-1 text-xs text-gray-500 whitespace-nowrap">
                                                <MapPin
                                                    size={11}
                                                    className="text-gray-300"
                                                />{" "}
                                                {u.city}
                                            </span>
                                        </td>

                                        <td className="px-4 py-3.5 text-sm font-bold text-gray-900">
                                            {u.orders.toLocaleString()}
                                        </td>

                                        <td className="px-4 py-3.5 text-sm font-bold text-gray-900">
                                            {u.spent ? (
                                                fmt(u.spent)
                                            ) : (
                                                <span className="text-gray-300 font-normal">
                                                    —
                                                </span>
                                            )}
                                        </td>

                                        <td className="px-4 py-3.5 text-xs text-gray-400 whitespace-nowrap">
                                            {u.lastActive}
                                        </td>

                                        <td className="px-4 py-3.5 text-xs text-gray-400 whitespace-nowrap">
                                            {u.joined}
                                        </td>

                                        <td
                                            className="px-4 py-3.5"
                                            onClick={(e) => e.stopPropagation()}
                                        >
                                            <StatusBadge status={u.status} />
                                        </td>

                                        {/* Actions */}
                                        <td
                                            className="px-4 py-3.5"
                                            onClick={(e) => e.stopPropagation()}
                                        >
                                            <div className="flex items-center gap-1.5">
                                                <button
                                                    onClick={() =>
                                                        setSelected(u)
                                                    }
                                                    className="p-1.5 rounded-lg bg-gray-50 hover:bg-blue-50 hover:text-blue-500 text-gray-500 transition-colors"
                                                    title="View"
                                                >
                                                    <Eye size={13} />
                                                </button>
                                                {u.status === "active" ? (
                                                    <button
                                                        onClick={() =>
                                                            handleToggleBan(
                                                                u.id
                                                            )
                                                        }
                                                        className="p-1.5 rounded-lg bg-red-50 hover:bg-red-100 text-red-500 transition-colors"
                                                        title="Ban user"
                                                    >
                                                        <Ban size={13} />
                                                    </button>
                                                ) : (
                                                    <button
                                                        onClick={() =>
                                                            handleToggleBan(
                                                                u.id
                                                            )
                                                        }
                                                        className="p-1.5 rounded-lg bg-emerald-50 hover:bg-emerald-100 text-emerald-600 transition-colors"
                                                        title="Unban user"
                                                    >
                                                        <Check size={13} />
                                                    </button>
                                                )}
                                            </div>
                                        </td>
                                    </tr>
                                );
                            })
                        )}
                    </tbody>
                </table>

                {/* Footer */}
                <div className="px-5 py-3 border-t border-gray-50 flex items-center justify-between">
                    <span className="text-xs text-gray-400">
                        Showing {filtered.length} of {userList.length} users
                    </span>
                    <div className="flex items-center gap-1">
                        <button className="w-7 h-7 rounded-lg bg-rose-500 text-white text-xs font-bold">
                            1
                        </button>
                        <button className="w-7 h-7 rounded-lg hover:bg-gray-100 text-gray-500 text-xs font-medium">
                            2
                        </button>
                    </div>
                </div>
            </div>

            {/* Drawer */}
            <UserDrawer
                user={selected}
                onClose={() => setSelected(null)}
                onToggleBan={handleToggleBan}
            />
        </div>
    );
}
