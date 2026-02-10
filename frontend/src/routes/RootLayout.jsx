

import { Outlet } from "react-router-dom";
import Navbar from "../component/navbar/Navbar";
import Footer from "../component/Footer/Footer";
import CartAside from "../component/cart/CartAside";
import { useState } from "react";


export default function RootLayout() {
  const [cartOpen, setCartOpen] = useState(false);

  return (
    <div className="app-wrapper ">
      <Navbar onCartOpen={ ()=> setCartOpen(true)} />
      <CartAside open={cartOpen} onClose={()=> setCartOpen(false)}/> 

      {/* Main page content */}
      <main className="min-h-[calc(100vh-120px)]  bg-gradient-to-br from-orange-50 via-white to-orange-100">
        <Outlet />
      </main>

      <Footer />
    </div>
  );
};