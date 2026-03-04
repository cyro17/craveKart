import { TrendingDown, TrendingUp } from "lucide-react";
import React from "react";

export default function KpiCard({
    label,
    rawValue,
    display,
    trend,
    trendLabel,
    icon: Icon,
    accent,
    delay,
}) {
    const [visible, setVisible] = React.useState(true);
    const isUp = trend >= 0;
    return (
        <div
            className="bg-white rounded-2xl border border-gray-100 shadow-sm p-5 hover:shadow-lg transition-all duration-500 cursor-default relative overflow-hidden group"
            style={{
                opacity: visible ? 1 : 0,
                transform: visible ? "translateY(0)" : "translateY(16px)",
                transition: `opacity 0.5s ease ${delay}ms, transform 0.5s ease ${delay}ms, box-shadow 0.2s`,
            }}
        >
            {/* subtle accent bar */}
            <div
                className={`absolute top-0 left-0 right-0 h-0.5 ${accent} opacity-0 group-hover:opacity-100 transition-opacity duration-300`}
            />

            <div className="flex items-start justify-between mb-4">
                <div className="flex items-start justify-between mb-4">
                    <Icon size={18} className="text-white" />
                </div>
                <div
                    className={`flex items-center gap-1 text-xs font-bold px-2 py-1 rounded-full ${
                        isUp
                            ? "bg-emerald-50 text-emerald-700"
                            : "bg-red-50 text-red-600"
                    }`}
                >
                    {isUp ? (
                        <TrendingUp size={11} />
                    ) : (
                        <TrendingDown size={11} />
                    )}
                    {Math.abs(trend)}%
                </div>
            </div>
            <div className="text-2xl font-black text-gray-500 tracking-tight leading-none">
                {display}
            </div>
            <div className="text-sm font-semibold text-gray-500 mt-1.5">
                {label}
            </div>
            <div className="text-[10px] text-gray-400 mt-1">{trendLabel}</div>
        </div>
    );
}
