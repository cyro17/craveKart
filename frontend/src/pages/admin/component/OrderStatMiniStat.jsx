import React from "react";

export default function OrderStatMiniStat({ label, value, color, icon: Icon }) {
    return (
        <div className="flex items-center gap-3 bg-white rounded-xl border border-gray-100 px-4 py-3 shadow-sm">
            <div
                className={`w-8 h-8 rounded-lg flex items-center justify-center ${color}`}
            >
                <Icon size={15} className="text-white" />
            </div>
            <div>
                <div className="text-lg font-black text-gray-900 leading-none">
                    {value}
                </div>
                <div className="text-[10px] text-gray-500 font-medium mt-0.5">
                    {label}
                </div>
            </div>
        </div>
    );
}
