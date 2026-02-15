import React from "react";
import { useSelector } from "react-redux";
import { Navigate, useLocation } from "react-router-dom";

export default function ProtectedRoute({ children }) {
    const { isAuthenticated } = useSelector((state) => state.auth);
    const location = useLocation();
    console.log(location.pathname);
    if (!isAuthenticated) {
        return (
            <Navigate
                to="/account/login"
                replace
                state={{ from: location.pathname }}
            />
        );
    }
    return children;
}
