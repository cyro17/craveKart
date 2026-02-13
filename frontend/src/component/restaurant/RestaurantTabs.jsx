import React from "react";
import { NavLink, useParams } from "react-router-dom";

export default function RestaurantTabs() {
    const { city, slug } = useParams();
    // console.log("tabs data: ", data);

    const tabStyle = ({ isActive }) =>
        `px-6 text-lg font-bold transition-all duration-200  border-b-2
    ${
        isActive
            ? "border-black text-black"
            : "border-transparent text-gray-500 hover:text-black"
    }
    `;

    return (
        <div className="text-stone-900">
            {/* RestaurantTabs */}

            <div>
                <NavLink to="." end className={tabStyle}>
                    Menu
                </NavLink>

                <NavLink to="reviews" end className={tabStyle}>
                    Review
                </NavLink>

                <NavLink to="info" end className={tabStyle}>
                    Info
                </NavLink>
            </div>
        </div>
    );
}
