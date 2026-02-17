import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import HomeIcon from "@mui/icons-material/Home";

export default function NotFound() {
    const navigate = useNavigate();
    const [countdown, setCountdown] = useState(3);

    useEffect(() => {
        const interval = setInterval(() => {
            setCountdown((prev) => prev - 1);
        }, 1000);

        const timer = setTimeout(() => {
            navigate("/", { replace: true });
        }, 3000);
        return () => {
            clearTimeout(timer);
            clearInterval(interval);
        };
    }, [navigate]);

    return (
        <div className="flex flex-col h-screen justify-center items-center">
            {/* Illustration or icon */}
            <div className="w-30 h-30 mb-6 text-blue-500">
                <HomeIcon className="w-full h-full animate-bounce" />
            </div>
            <h1 className="text-5xl font-bold text-gray-800 mb-2">Oops!</h1>
            <p className="text-xl text-gray-600 mb-6">
                We couldn’t find the page you were looking for.
            </p>
            <div className="w-16 h-16 border-4 border-blue-500 border-t-transparent rounded-full animate-spin mb-2" />
            <div className="mt-4 text-xl text-gray-500">
                Redirecting to home in {countdown} second{" "}
                {countdown > 1 ? "s" : ""}...
            </div>
        </div>
    );
}
