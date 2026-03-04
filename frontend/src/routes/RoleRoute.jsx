import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Navigate } from "react-router-dom";
import { openAuth } from "../State/ui/uiSlice";

function RoleRoute({ children, role }) {
    const dispatch = useDispatch();
    const { isAuthenticated, user } = useSelector((state) => state.auth); // Assuming you have an authentication context

    useEffect(() => {
        if (!isAuthenticated) {
            dispatch(openAuth({ authMode: "login" }));
        }
    }, [isAuthenticated, dispatch]);

    if (!isAuthenticated) return null;

    const allowed = Array.isArray(role)
        ? role.includes(user?.role)
        : user?.role === role;

    if (!allowed) {
        return <Navigate to="/" replace />;
    }

    return children;
}

export default RoleRoute;
