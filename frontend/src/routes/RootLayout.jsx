

import { Outlet } from "react-router-dom";
import Navbar from "../component/navbar/Navbar";
import Footer from "../component/Footer/Footer";
import CartAside from "../component/cart/CartAside";
import { useState } from "react";
import AuthDrawer from "../component/Auth/AuthDrawer";


export default function RootLayout() {
  const [cartOpen, setCartOpen] = useState(false);

  const [authOpen, setAuthOpen] = useState(false);
  const [authMode, setAuthMode] = useState("login");

  const openLogin = () => {
    setAuthMode("login");
    setAuthOpen(true);
  };

  const openRegister = () => {
    setAuthMode("register");
    setAuthOpen(true);
  };


  return (
    <div className="app-wrapper flex flex-col justify-center">
      <Navbar onCartOpen={() => setCartOpen(true)}
        openAuthLogin={openLogin}
        openAuthRegister={openRegister}
      />
      <CartAside open={cartOpen} onClose={()=> setCartOpen(false)}/> 

      {/* Main page content */}
      <main className="min-h-[calc(100vh-120px)]  bg-gradient-to-br from-rose-50 via-white to-pink-100">
        <Outlet />
      </main>
      <AuthDrawer
        open={authOpen}
        onClose={() => setAuthOpen(false)}
        mode={authMode}
        setMode={setAuthMode}
      />
      <Footer className="" />
    </div>
  );
};