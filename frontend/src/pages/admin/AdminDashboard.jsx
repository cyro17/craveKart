import React, { useEffect, useState } from "react";
import KpiCard from "./component/KpiCard";
import {
    AlertTriangle,
    Bike,
    CheckCircle,
    Clock,
    IndianRupee,
    ShoppingBag,
    Star,
    Users,
    UtensilsCrossed,
    XCircle,
} from "lucide-react";
import OrderStatMiniStat from "./component/OrderStatMiniStat";

import {
    Area,
    AreaChart,
    CartesianGrid,
    PieChart,
    Pie,
    ResponsiveContainer,
    Tooltip,
    XAxis,
    Cell,
} from "recharts";

const revenueData = [
    { day: "Mon", revenue: 42000, orders: 134 },
    { day: "Tue", revenue: 58000, orders: 189 },
    { day: "Wed", revenue: 51000, orders: 162 },
    { day: "Thu", revenue: 73000, orders: 241 },
    { day: "Fri", revenue: 89000, orders: 298 },
    { day: "Sat", revenue: 95000, orders: 321 },
    { day: "Sun", revenue: 67000, orders: 215 },
];

const categoryData = [
    { name: "Biryani", value: 32, color: "#f43f5e" },
    { name: "Burgers", value: 24, color: "#fb923c" },
    { name: "Pizza", value: 20, color: "#fbbf24" },
    { name: "Chinese", value: 15, color: "#34d399" },
    { name: "Others", value: 9, color: "#a78bfa" },
];

const topRestaurants = [
    {
        name: "Biryani Blues",
        orders: 892,
        revenue: 284000,
        rating: 4.7,
        city: "Delhi",
    },
    {
        name: "Pizza Palace",
        orders: 654,
        revenue: 198000,
        rating: 4.5,
        city: "Bangalore",
    },
    {
        name: "Tandoor Tales",
        orders: 445,
        revenue: 142000,
        rating: 4.6,
        city: "Pune",
    },
    {
        name: "Wok This Way",
        orders: 201,
        revenue: 62000,
        rating: 3.9,
        city: "Hyderabad",
    },
];

const recentOrders = [
    {
        id: "#CK-8821",
        customer: "Ananya Roy",
        restaurant: "Biryani Blues",
        amount: 640,
        status: "delivered",
        time: "2m ago",
    },
    {
        id: "#CK-8820",
        customer: "Rishi Kapoor",
        restaurant: "Pizza Palace",
        amount: 420,
        status: "in-transit",
        time: "8m ago",
    },
    {
        id: "#CK-8819",
        customer: "Meera Nair",
        restaurant: "Burger Barn",
        amount: 199,
        status: "preparing",
        time: "12m ago",
    },
    {
        id: "#CK-8818",
        customer: "Arjun Dev",
        restaurant: "Tandoor Tales",
        amount: 890,
        status: "delivered",
        time: "18m ago",
    },
    {
        id: "#CK-8817",
        customer: "Kavya Reddy",
        restaurant: "Wok This Way",
        amount: 350,
        status: "cancelled",
        time: "25m ago",
    },
];

const statusConfig = {
    delivered: {
        label: "Delivered",
        cls: "bg-emerald-50 text-emerald-700 border-emerald-200",
    },
    "in-transit": {
        label: "In Transit",
        cls: "bg-blue-50 text-blue-700 border-blue-200",
    },
    preparing: {
        label: "Preparing",
        cls: "bg-amber-50 text-amber-700 border-amber-200",
    },
    cancelled: {
        label: "Cancelled",
        cls: "bg-gray-100 text-gray-500 border-gray-200",
    },
};

const pendingApprovals = [
    {
        name: "Burger Barn",
        owner: "Priya Singh",
        city: "Mumbai",
        joined: "Mar 2024",
    },
    {
        name: "The Curry House",
        owner: "Vikram Rao",
        city: "Chennai",
        joined: "Mar 2024",
    },
];

const fmt = (n) => `₹${n.toLocaleString("en-IN")}`;

function useCountUp(target, duration = 1200, delay = 0) {
    const [value, setValue] = useState(0);

    useEffect(() => {
        const timeout = setTimeout(() => {
            const start = Date.now();

            const tick = () => {
                const elapsed = Date.now() - start;
                const progress = Math.min(elapsed / duration, 1);
                const eased = 1 - Math.pow(1 - progress, 3);
                setValue(Math.floor(eased * target));
                if (progress < 1) requestAnimationFrame(tick);
            };
            tick();
        }, delay);

        return () => clearTimeout(timeout);
    }, [target, duration, delay]);
    return value;
}

function StatusBadge({ status }) {
    const cfg = statusConfig[status] || {
        label: status,
        cls: "bg-gray-100 text-gray-500 border-gray-200",
    };
    return (
        <span
            className={`text-[10px] font-bold px-2 py-0.5 rounded-full border ${cfg.cls}`}
        >
            {cfg.label}
        </span>
    );
}

export default function AdminDashboard() {
    const [range, setRange] = useState("7d");

    const revenue = useCountUp(840000, 1400, 0);
    const orders = useCountUp(3892, 1200, 100);
    const restaurants = useCountUp(47, 1000, 200);
    const users = useCountUp(14280, 1300, 300);

    return (
        <div className=" p=[28px]">
            {/* Header */}
            <div className="flex items-center justify-between mb-6 ">
                <div>
                    <h1 className="text-xl font-black text-gray-900 tracking-tight">
                        Dashboard
                    </h1>
                    <p className="text-xs text-gray-400 mt-0.5">
                        Wednesday, March 4, 2026. All data is live
                    </p>
                </div>

                <div>
                    <button>{range}</button>
                </div>
            </div>

            {/* Kpi Card */}
            <div className="grid grid-cols-4 gap-4 mb-5">
                <KpiCard
                    label="Total Revenue"
                    display={`₹${(revenue / 100000).toFixed(1)}L`}
                    trend={12.4}
                    trendLabel="vs last month"
                    icon={IndianRupee}
                    accent="bg-rose-500"
                    delay={0}
                />
                <KpiCard
                    label="Total Orders"
                    display={orders.toLocaleString()}
                    trend={8.1}
                    trendLabel="vs last month"
                    icon={ShoppingBag}
                    accent="bg-orange-500"
                    delay={80}
                />
                <KpiCard
                    label="Active Restaurants"
                    display={restaurants}
                    trend={4.2}
                    trendLabel="2 pending approval"
                    icon={UtensilsCrossed}
                    accent="bg-amber-500"
                    delay={160}
                />
                <KpiCard
                    label="Registered Users"
                    display={users.toLocaleString()}
                    trend={6.7}
                    trendLabel="↑ 284 this week"
                    icon={Users}
                    accent="bg-emerald-500"
                    delay={240}
                />
            </div>

            {/* Order status Mini Row */}

            <div className="grid grid-cols-4 gap-3 mb-5">
                <OrderStatMiniStat
                    label="Delivered Today"
                    value="218"
                    color="bg-emerald-500"
                    icon={CheckCircle}
                />
                <OrderStatMiniStat
                    label="In Transit"
                    value="34"
                    color="bg-blue-500"
                    icon={Bike}
                />
                <OrderStatMiniStat
                    label="Preparing"
                    value="21"
                    color="bg-amber-500"
                    icon={Clock}
                />
                <OrderStatMiniStat
                    label="Cancelled Today"
                    value="10"
                    color="bg-red-500"
                    icon={XCircle}
                />
            </div>

            {/* Charts  */}
            <div className="grid grid-cols-3 gap-4 mb-5">
                {/* Revenue + Orders Area  */}

                <div className="col-span-2 bg-white rounded-2xl border border-gray-100 shadow-sm p-5">
                    <div className="flex items-center justify-between mb-5">
                        <div>
                            <h2 className="text-sm font-bold text-gray-900">
                                Revenue & Orders
                            </h2>
                            <p className="text-xs text-gray-400 mt-0.5">
                                Last & days performance
                            </p>
                        </div>

                        <div className="flex gap-4 text-[11px] text-gray-500">
                            <span className="flex items-center gap-1.5">
                                <span className="w-2.5 h-2.5 rounded-full bg-rose-500" />
                                Revenue
                            </span>
                            <span className="flex items-center gap-1.5">
                                <span className="w-2.5 h-2.5 rounded-full bg-orange-500" />
                                Orders
                            </span>
                        </div>
                    </div>

                    <ResponsiveContainer width="100%" height={200}>
                        <AreaChart
                            data={revenueData}
                            margin={{ top: 4, right: 4, left: 0, bottom: 0 }}
                        >
                            <defs>
                                <linearGradient
                                    id="gRev"
                                    x1="0"
                                    y1="0"
                                    x2="0"
                                    y2="1"
                                >
                                    <stop
                                        offset="5%"
                                        stopColor="#f43f5e"
                                        stopOpacity={0.18}
                                    />
                                    <stop
                                        offset="95%"
                                        stopColor="#f43f5e"
                                        stopOpacity={0}
                                    />
                                </linearGradient>

                                <linearGradient
                                    id="gOrd"
                                    x1="0"
                                    y1="0"
                                    x2="0"
                                    y2="1"
                                >
                                    <stop
                                        offset="5%"
                                        stopColor="#fb923c"
                                        stopOpacity={0.15}
                                    />
                                    <stop
                                        offset="95%"
                                        stopColor="#fb923c"
                                        stopOpacity={0}
                                    />
                                    <stop offset="" />
                                </linearGradient>
                            </defs>

                            <CartesianGrid
                                strokeDasharray="3 3"
                                stroke="#f1f5f9"
                                vertical={false}
                            />
                            <XAxis
                                dataKey="day"
                                tick={{
                                    fontSize: 11,
                                    fill: "#94a3b8",
                                }}
                                axisLine={false}
                                tickLine={false}
                                tickFormatter={(v) => `₹${v / 1000}k`}
                                width={42}
                            />
                            <Tooltip />
                            <Area
                                type="monotone"
                                dataKey="revenue"
                                stroke="#f43f5e"
                                strokeWidth={2.5}
                                fill="url(#gRev)"
                                dot={{
                                    fill: "$f43f5e",
                                    r: 3.5,
                                    strokeWidth: 0,
                                }}
                                activeDot={{ r: 5 }}
                            />
                            <Area
                                type="monotone"
                                dataKey="orders"
                                stroke="#fb923c"
                                strokeWidth={2}
                                fill="url(#gOrd)"
                                dot={{ fill: "#fb923c", r: 3, strokeWidth: 0 }}
                                activeDot={{ r: 4 }}
                            />
                        </AreaChart>
                    </ResponsiveContainer>
                </div>

                <div className="bg-white rounded-2xl border-gray-100 shadow-sm p-5">
                    <h2 className="text-sm font-bold text-gray-900">
                        Orders by Category
                    </h2>
                    <p className="text-xs text-gray-400 mb-3 mt-0.5">
                        This month
                    </p>

                    <ResponsiveContainer width="100%" height={180}>
                        <PieChart>
                            <Pie
                                data={categoryData}
                                cx="50%"
                                cy="50%"
                                innerRadius={42}
                                paddingAngle={3}
                                dataKey="value"
                                strokeWidth={0}
                                outerRadius={66}
                            >
                                {categoryData.map((e, i) => (
                                    <Cell key={i} fill={e.color} />
                                ))}
                            </Pie>
                            <Tooltip
                                contentStyle={{
                                    fontSize: 11,
                                    borderRadius: 8,
                                    border: "1px solid $f1f5f9",
                                }}
                                formatter={(v) => [`${v}%`, ""]}
                            />
                        </PieChart>
                    </ResponsiveContainer>

                    <div className="space-y-1.5">
                        {categoryData.map((c) => (
                            <div
                                key={c.name}
                                className="flex items-center justify-between text-xs"
                            >
                                <span className="flex items-center gap-2 text-gray-600">
                                    <span
                                        className="inline-block w-2 h-2 rounded-full flex-shrink-0"
                                        style={{ background: c.color }}
                                    />

                                    {c.name}
                                </span>
                                <span className="font-bold text-gray-900">
                                    {c.value}
                                </span>
                            </div>
                        ))}
                    </div>
                </div>
            </div>

            {/* Bottom Row : Recent Orders + Top Restaurants + pending */}

            <div className="grid grid-cols-3 gap-4">
                {/* Recent Orders */}
                <div className="col-span-1 bg-white rounded-2xl border border-gray-100 shadow-sm p-5">
                    <div className="flex items-center justify-between mb-4">
                        <h2 className="text-sm font-bold text-gray-900">
                            Recent Orders
                        </h2>
                        <button className="text-[11px] font-bold text-rose-500 hover: underline">
                            View All{"->"}
                        </button>
                    </div>

                    <div>
                        {recentOrders.map((o) => (
                            <div className="flex items-center gap-3 py-2 border-b border-gray-50 last:border-none">
                                <div className="w-7 h-7 rounded-lg bg-rose-50 flex items-center justify-center flex-shrink-0">
                                    <ShoppingBag
                                        size={12}
                                        className="text-rose-500"
                                    />
                                </div>
                                <div className="flex-1 min-w-0">
                                    <div className="text-xs font-semibold text-gray-900 truncate">
                                        {o.customer}
                                    </div>
                                    <div className="text-[10px] text-gray-400">
                                        {o.restaurant}
                                    </div>
                                </div>
                                <div className="flex-shrink-0 text-right">
                                    <div className="text-xs font-semibold text-gray-900 truncate ">
                                        ₹{o.amount}
                                    </div>
                                    <StatusBadge status={o.status} />
                                </div>
                            </div>
                        ))}
                    </div>
                </div>

                {/* Top Restaurants */}
                <div className="col-span-1 bg-white rounded-2xl border border-gray-100 shadow-sm p-5">
                    <div className="flex items-center justify-between mb-4">
                        <h2 className="text-sm font-bold text-gray-900">
                            Top Restaurants
                        </h2>
                        <button className="text-[11px] font-bold text-rose500 hover:underline">
                            View All {"->"}
                        </button>
                    </div>
                    <div className="space-y-3">
                        {topRestaurants.map((r, i) => (
                            <div
                                key={r.name}
                                className="flex items-center gap-3"
                            >
                                <div
                                    key={r.name}
                                    className={`w-6 h-6 rounded-full flex items-center justify-center text-[10px] font-black flex-shrink-0
                                    ${
                                        i === 0
                                            ? "bg-amber-100 text-amber-600"
                                            : i === 1
                                            ? "bg-gray-100 text-gray-500"
                                            : "bg-orange-50 text-orange-400"
                                    }`}
                                >
                                    {i + 1}
                                </div>
                                <div className="flex-1 min-w-0">
                                    <span className="text-xs font-semibold text-gray-900 truncate">
                                        {r.name}
                                    </span>
                                    <div className="text-[10px] text-gray-400 ">
                                        {r.city} · {r.orders}
                                    </div>
                                    <div className="text-right flex-shrink-0">
                                        <div className="text-xs font-semibold text-gray-900 truncate">
                                            {fmt(r.revenue)}
                                        </div>
                                        <div className="flex items-center justify-end gap-0.5 text-[10px] text-amber-500 font-semibold">
                                            <Star
                                                size={9}
                                                fill="currentColor"
                                            />
                                            {r.rating}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>

                {/* Pending Approvals */}
                <div className="col-span-1 bg-white rounded-2xl border border-gray-100 shadow-sm p-5">
                    <div className="flex items-center justify-between mb-4">
                        <h2 className="text-sm font-bold text-gray-900">
                            Pending Approvals
                        </h2>
                        <span className="text-[10px] bg-amber-100 text-amber-700 font-black px-2 py-0.5 rounded-full border border-amber-200">
                            {pendingApprovals.length} new{" "}
                        </span>
                    </div>
                    <div className="space-y-3">
                        {pendingApprovals.map((r) => (
                            <div className="rounded-xl border border-amber-100 bg-gradient-to-br from-amber-50/60 to-white p-3">
                                <div className="flex items-start gap-2.5">
                                    <div className="w-8 h-8 rounded-xl bg-amber-100 flex items-center justify-center text-amber-600 font-black text-sm flex-shrink-0">
                                        {r.name[0]}
                                    </div>
                                    <div className="flex-1 min-w-0">
                                        <div className="text-xs font-bold text-gray-900">
                                            {r.name}
                                        </div>
                                        <div className="text-[10px] text-gray-500">
                                            {r.owner}
                                        </div>
                                        <div className="text-[10px] text-gray-400">
                                            Applied {r.joined}
                                        </div>
                                    </div>
                                    <AlertTriangle
                                        size={13}
                                        className="text-amber-400 flex-shrink-0 mt-0.5"
                                    />
                                </div>
                                <div className="flex gap-2 mt-2.5">
                                    <button className="flex-1 text-[11px] font-bold bg-emerald-500 hover:bg-emerald-600 text-white py-1.5 rounded-lg transition-colors">
                                        ✓ Approve
                                    </button>
                                    <button className="flex-1 text-[11px] font-bold bg-gray-100 hover:bg-red-50 hover:text-red-500 rounded-lg transition-colors">
                                        {" "}
                                        ✕ Reject
                                    </button>
                                </div>
                            </div>
                        ))}
                    </div>
                    {/* Quick Summary */}
                    <div className="mt-4 pt-3 border-t border-gray-50">
                        <div className="text-[10px] text-gray-400 font-medium mb-2">
                            Platform Health
                        </div>
                        <div className="space-y-1.5">
                            {[
                                {
                                    label: "Uptime",
                                    value: "99.9%",
                                    color: "bg-emerald-500",
                                    width: "99%",
                                },
                                {
                                    label: "Avg delivery",
                                    value: "28 min",
                                    color: "bg-blue-400",
                                    width: "70%",
                                },
                                {
                                    label: "Satisfaction",
                                    value: "4.6 ★",
                                    color: "bg-amber-400",
                                    width: "92%",
                                },
                            ].map((m) => (
                                <div
                                    key={m.label}
                                    className="flex items-center gap-2"
                                >
                                    <div className="text-[10px] text-gray-500 w-20 flex-shrink-0">
                                        {m.label}
                                    </div>
                                    <div className="flex-1 h-1.5 bg-gray-100 rounded-full overflow-hidden">
                                        <div
                                            className={`h-full ${m.color} rounded-full`}
                                            style={{ width: m.width }}
                                        />
                                    </div>
                                    <div className="text-[10px] font-bold text-gray-700 w-12 text-right flex-shrink-0">
                                        {m.value}
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
