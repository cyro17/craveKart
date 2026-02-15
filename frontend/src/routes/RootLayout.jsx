import { Outlet } from "react-router-dom";
import Navbar from "../component/navbar/Navbar";
import Footer from "../component/Footer/Footer";
import CartAside from "../component/cart/CartAside";
import AuthDrawer from "../component/Auth/AuthDrawer";
import { useDispatch, useSelector } from "react-redux";
import {
    closeAuth,
    closeCart,
    openAuth,
    openCart,
    setAuthMode,
} from "../State/ui/uiSlice";

import { Toaster } from "react-hot-toast";

export default function RootLayout() {
    const dispatch = useDispatch();
    const { cartOpen, authOpen, authMode } = useSelector((state) => state.ui);

    // Handlers
    const handelOpenLogin = () => dispatch(openAuth("login"));
    const handleOpenRegister = () => dispatch(openAuth("Register"));
    const handleOpenCart = () => dispatch(openCart());

    const handleCloseAuth = () => dispatch(closeAuth());
    const handleCloseCart = () => dispatch(closeCart());

    const handleSetAuthMode = (mode) => dispatch(setAuthMode(mode));

    // const openRegister = () => {
    //     setAuthMode("register");
    //     setAuthOpen(true);
    // };

    return (
        <>
            <Toaster position="bottom-left" reverseOrder={false} />
            <div className="app-wrapper flex flex-col justify-center">
                <Navbar
                    onCartOpen={handleOpenCart}
                    openAuthLogin={handelOpenLogin}
                    openAuthRegister={handleOpenRegister}
                />
                <CartAside open={cartOpen} onClose={handleCloseCart} />

                {/* Main page content */}
                <main className="min-h-[calc(100vh-120px)]  bg-gradient-to-br from-rose-50 via-white to-pink-100">
                    <Outlet />
                </main>
                <AuthDrawer
                    open={authOpen}
                    onClose={handleCloseAuth}
                    mode={authMode}
                    setMode={handleSetAuthMode}
                />
                <Footer className="" />
            </div>
        </>
    );
}
