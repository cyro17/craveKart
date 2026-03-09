import React, { useState } from "react";
import SummaryCards from "./component/SummaryCards";
import {
    Download,
    Plus,
    Search,
    MapPin,
    Star,
    Check,
    X,
    Ban,
    Eye,
} from "lucide-react";

const restaurants = [
    {
        id: 1,
        name: "Biryani Blues",
        owner: "Rahul Sharma",
        email: "rahul@biryaniblues.com",
        phone: "+91 98100 11234",
        city: "Delhi",
        cuisine: "North Indian",
        status: "active",
        orders: 892,
        rating: 4.7,
        revenue: 284000,
        joined: "Jan 2024",
        commission: 10,
    },
    {
        id: 2,
        name: "Burger Barn",
        owner: "Priya Singh",
        email: "priya@burgerbarn.com",
        phone: "+91 99200 22345",
        city: "Mumbai",
        cuisine: "Fast Food",
        status: "pending",
        orders: 0,
        rating: null,
        revenue: 0,
        joined: "Mar 2024",
        commission: 10,
    },
    {
        id: 3,
        name: "Pizza Palace",
        owner: "Amit Kumar",
        email: "amit@pizzapalace.com",
        phone: "+91 97300 33456",
        city: "Bangalore",
        cuisine: "Italian",
        status: "active",
        orders: 654,
        rating: 4.5,
        revenue: 198000,
        joined: "Feb 2024",
        commission: 10,
    },
    {
        id: 4,
        name: "Wok This Way",
        owner: "Neha Patel",
        email: "neha@wokthisway.com",
        phone: "+91 96400 44567",
        city: "Hyderabad",
        cuisine: "Chinese",
        status: "suspended",
        orders: 201,
        rating: 3.9,
        revenue: 62000,
        joined: "Dec 2023",
        commission: 10,
    },
    {
        id: 5,
        name: "The Curry House",
        owner: "Vikram Rao",
        email: "vikram@curryhouse.com",
        phone: "+91 95500 55678",
        city: "Chennai",
        cuisine: "South Indian",
        status: "pending",
        orders: 0,
        rating: null,
        revenue: 0,
        joined: "Mar 2024",
        commission: 10,
    },
    {
        id: 6,
        name: "Tandoor Tales",
        owner: "Sunita Mehta",
        email: "sunita@tandoortales.com",
        phone: "+91 94600 66789",
        city: "Pune",
        cuisine: "North Indian",
        status: "active",
        orders: 445,
        rating: 4.6,
        revenue: 142000,
        joined: "Nov 2023",
        commission: 12,
    },
    {
        id: 7,
        name: "Dosa Delight",
        owner: "Karthik Nair",
        email: "karthik@dosadelight.com",
        phone: "+91 93700 77890",
        city: "Bangalore",
        cuisine: "South Indian",
        status: "active",
        orders: 334,
        rating: 4.4,
        revenue: 98000,
        joined: "Oct 2023",
        commission: 10,
    },
    {
        id: 8,
        name: "Rolls & Wraps",
        owner: "Sneha Gupta",
        email: "sneha@rollswraps.com",
        phone: "+91 92800 88901",
        city: "Delhi",
        cuisine: "Street Food",
        status: "active",
        orders: 278,
        rating: 4.2,
        revenue: 74000,
        joined: "Sep 2023",
        commission: 10,
    },
];

const FILTERS = ["all", "active", "pending", "suspended"];

function StatusBadge({ status }) {
    const map = {
        active: "bg-emerald-50 text-emerald-700 border-emerald-200",
        pending: "bg-amber-50 text-amber-700 border-amber-200",
        suspended: "bg-red-50 text-red-600 border-red-200",
    };
    return (
        <span
            className={`text-xs font-semibold px-2.5 py-1 rounded-full border capitalize ${map[status]}`}
        >
            {status}
        </span>
    );
}

export default function AdminRestaurants() {
    const [filter, setFilter] = useState("all");
    const [search, setSearch] = useState("");
    const [selected, setSelected] = useState(null);

    const filtered = restaurants.filter((r) => {
        const matchFilter = filter === "all" || r.status === filter;
        const matchSearch =
            r.name.toLowerCase().includes(search.toLowerCase) ||
            r.owner.toLowerCase().includes(search.toLowerCase()) ||
            r.city.toLowerCase().includes(search.toLowerCase());

        return matchFilter && matchSearch;
    });

    return (
        <div style={{ fontFamily: "'DM Sans', system-ui, sans-serif" }}>
            <SummaryCards data={restaurants} />

            {/* Toolbar */}
            <div className="flex  items-center gap-3 mb-4">
                {/* Filter tabs */}
                <div
                    className="flex items-center gap-1.5 bg-white border border-gray-200 
                rounded-xl p-1 shadow-sm"
                >
                    {FILTERS.map((f) => {
                        const count =
                            f === "all"
                                ? restaurants.length
                                : restaurants.filter((r) => r.status === f)
                                      .length;
                        return (
                            <button
                                key={f}
                                onClick={() => setFilter(f)}
                                className={`text-xs font-bold px-3 py-1.5 rounded-lg capitalize transition-all
                                ${
                                    filter === f
                                        ? "bg-rose-500 text-white shadow-sm"
                                        : "text-gray-500 hover:text-gray-900"
                                }
                                    `}
                            >
                                {f}
                                <span
                                    className={`ml-1.5 text-[10px] px-1.5 py-0.5 rounded-full  
                                    ${
                                        filter === f
                                            ? "bg=white/25 text-white"
                                            : "bg-gray-100 text-gray-500"
                                    }`}
                                >
                                    {count}
                                </span>
                            </button>
                        );
                    })}
                </div>

                <div>
                    <Search
                        size={14}
                        className="absolute left-3 top-1/2 -translate-y-1/2"
                    />
                    <input
                        type="text"
                        value={search}
                        onChange={(e) => setSearch(e.target.value)}
                        placeholder="Search name, owner, city..."
                        className="w-full pl-9 pr-4 py-2 text-sm bg-white border border-gray-200 rounded-xl
                            focus:outline-none focus:ring-2 focus:ring-rose-200 focus:border-rose-300 transition-all shadow-sm
                        "
                    />
                </div>
                <div className="ml-auto flex items-center gap-2">
                    <button
                        className="flex items-center gap-2 text-sm px-3 py-2 bg-white border border-gray-200
                            rounded-xl text-gray-600 hover:bg-gray-50 transition-colors shadow-sm font-medium"
                    >
                        <Download size={14} />
                        Export
                    </button>
                    <button
                        className="flex items-center gap-2 text-sm px-4 py-2 bg-rose-500 hover:bg-rose-600
                    text-white rounded-xl font-bold transition-colors shadow-sm"
                    >
                        <Plus size={15} /> Add Restaurant
                    </button>
                </div>
            </div>

            <div>
                <table className="w-full">
                    <thead>
                        <tr>
                            {[
                                "Restaurant",
                                "Owner",
                                "Location",
                                "Cuisine",
                                "Orders",
                                "Revenue",
                                "Rating",
                                "Status",
                                "Actions",
                            ].map((h) => (
                                <th className="text-left text-xs items-center font-bold text-gray-500 px-5 py-3.5">
                                    {h}
                                </th>
                            ))}
                        </tr>
                    </thead>
                    <tbody>
                        {filtered.length === 0 ? (
                            <tr>
                                <td>No restaurants found</td>
                            </tr>
                        ) : (
                            filtered.map((r) => (
                                <tr
                                    key={r.id}
                                    className="hover:bg-gray-50/60 transition-colors cursor-pointer"
                                    onClick={() => setSelected(r)}
                                >
                                    <td className="px-5 py-4">
                                        <div className="flex items-center gap-3">
                                            <div
                                                className="w-9 h-9 rounded-xl bg-gradient-to-br from-rose-100 to-orange-100 flex items-center 
                                            justify-center text-rose-500 font-black text-sm flex-shrink-0"
                                            >
                                                {r.name[0]}
                                            </div>
                                            <div>
                                                <div className="text-sm font-bold text-gray-900">
                                                    {r.name}
                                                </div>
                                                <div className="text-[10px] text-gray-400">
                                                    Since {r.joined}
                                                </div>
                                            </div>
                                        </div>
                                    </td>

                                    {/* Owner */}
                                    <td className="px-5 py-4 text-sm text-gray-700 font-medium">
                                        {r.owner}
                                    </td>

                                    {/* Location */}
                                    <td className="px-5 py-4">
                                        <span className="flex items-center gap-1 text-sm text-gray-500">
                                            <MapPin
                                                size={12}
                                                className="text-gray-300"
                                            />
                                            {r.city}
                                        </span>
                                    </td>

                                    {/* Cuisine */}
                                    <td className="px-5 py-4">
                                        <span className="text-xs bg-gray-100 font-medium text-gray-600  px-2 py-2 rounded-lg">
                                            {r.cuisine}
                                        </span>
                                    </td>

                                    {/* Orders */}
                                    <td className="px-5 py-4 text-sm font-bold text-gray-900">
                                        {r.orders.toLocaleString()}
                                    </td>

                                    {/* Revenue */}
                                    <td className="px-5 py-4">{r.revenue}</td>

                                    {/* Rating */}
                                    <td className="px-5 py-4 ">
                                        {r.rating ? (
                                            <span className="flex items-center gap-1 text-sm font-bold text-amber-500">
                                                <Star
                                                    size={13}
                                                    fill="currentColor"
                                                />
                                                {r.rating}
                                            </span>
                                        ) : (
                                            <span>-</span>
                                        )}
                                    </td>

                                    {/* Status */}
                                    <td className="px-5 py-4">
                                        <StatusBadge status={r.status} />
                                    </td>

                                    {/* Actions */}
                                    <td className="px-4 py-4">
                                        <div className="flex items-center gap-1.5">
                                            {r.status === "pending" && (
                                                <>
                                                    <button
                                                        className="p-1.5 rounded-lg bg-emerald-50 text-emerald-600 hover:bg-emerald-100 transition-colors"
                                                        title="Approve"
                                                    >
                                                        <Check size={14} />
                                                    </button>
                                                    <button
                                                        className="p-1.5 rounded-lg bg-red-50 text-red-600 hover:bg-red-100  transition-colors"
                                                        title="Reject"
                                                    >
                                                        <X size={14} />
                                                    </button>
                                                </>
                                            )}
                                            {r.status === "active" && (
                                                <button className="p-1.5 rounded-lg bg-gray-50 text-gray-600 hover:bg-red-50 hover:text-red-500 transition-colors">
                                                    <Ban size={14} />
                                                </button>
                                            )}
                                            {r.status === "suspended" && (
                                                <button className="p-1.5 rounded-lg bg-emerald-50 text-emerald-600 hover:bg-emerald-100 transition-colors">
                                                    <Check size={14} />
                                                </button>
                                            )}
                                            <button className="p-1.5 rounded-lg bg-gray-50 text-gray-500 hover:bg-blue-50 hover:text-blue-500 transition">
                                                <Eye size={14} />
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>

                {/* Table Footer */}
                <div className="flex justify-between">
                    <span className="text-xs">
                        Showing {filtered.length} of {restaurants.length}{" "}
                        restaurants
                    </span>
                    <div className="text-sm">
                        {[1, 2, 3].map((p) => (
                            <button className="">{p}</button>
                        ))}
                    </div>
                </div>

                {/* Detail Drawer */}
            </div>
        </div>
    );
}
