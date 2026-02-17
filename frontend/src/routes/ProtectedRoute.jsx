import { useDispatch, useSelector } from "react-redux";

import { openAuth } from "../State/ui/uiSlice";
import { useLocation } from "react-router-dom";
import { useEffect } from "react";

export default function ProtectedRoute({ children }) {
    const { isAuthenticated } = useSelector((state) => state.auth);
    // console.log(isAuthenticated);
    const location = useLocation();
    const dispatch = useDispatch();

    useEffect(() => {
        if (!isAuthenticated) {
            dispatch(
                openAuth({
                    redirectAfterLogin: location.pathname,
                    authMode: "login",
                })
            );
        }
    }, [isAuthenticated, dispatch, location.pathname]);

    if (isAuthenticated) return children;

    return null;
}
