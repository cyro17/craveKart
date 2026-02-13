import { createBrowserRouter, RouterProvider } from "react-router-dom";

// Layouts
import RootLayout from "./RootLayout";
import NotFound from "../component/NotFound/NotFound";
import Home from "../component/Home/Home";
import RestaurantLayout from "../component/restaurant/RestaurantLayout";
import RestaurantMenu from "../component/restaurant/RestaurantMenu";
import RestaurantInfo from "../component/restaurant/RestaurantInfo";
import RestaurantReviews from "../component/restaurant/RestaurantReviews";
import Auth from "../component/Auth/Auth";
import ProtectedRoute from "./ProtectedRoute";
import ProfileLayout from "../component/profile/ProfileLayout";
import RoleRoute from "./RoleRoute";
import LandingPage from "../component/Home/LandingPage";


// Guards
// import ProtectedRoute from "./ProtectedRoute";
// import RoleRoute from "./RoleRoute";

// Error
// import NotFound from "../component/NotFound/NotFound";

// Public
// import Home from "../component/Home/Home";
// import Cart from "../component/cart/Cart";
// import Auth from "../component/Auth/Auth";
// import RestaurantList from "../component/restaurant/RestaurantList";
// import RestaurantDetails from "../component/restaurant/RestaurantDetails";

// Profile
// import Profile from "../component/profile/Profile";
// import UserProfile from "../component/profile/UserProfile";
// import Orders from "../component/profile/Orders";
// import Favourites from "../component/profile/Favourites";
// import Address from "../component/profile/Address";
// import Events from "../component/profile/Events";
// import Payment from "../component/profile/Payment";
// import Logout from "../component/Auth/Logout";

// Restaurant Admin
// import AdminLayout from "../component/admin/AdminLayout";
// import RestaurantDashboard from "../component/admin/RestaurantDashboard";
// import RestaurantOrders from "../component/admin/RestaurantOrders";
// import RestaurantMenu from "../component/admin/RestaurantMenu";
// import CreateRestaurantForm from "../component/admin/CreateRestaurantForm";

const rootRouter = createBrowserRouter([
  {
    path: "/",
    element: <RootLayout />,
    children: [
      //landing page
      {
        index: true, 
        element: <LandingPage />  
      },
      //  Home
      {
        path: ":city",
        element: <Home />
      },

      //  Restaurants
        {
            path: ":city/restaurant/:id",
            element: <RestaurantLayout />,
            children: [
                {
                    index: true, element: <RestaurantMenu />
                }, 
                { 
                    path: "reviews", element: <RestaurantReviews />
                }, 
                { 
                    path: "info", element: <RestaurantInfo />
                }
            ]
        },

      //  Cart
        // { path: "cart", element: <Cart /> },
        // {
        //     path: "checkout",
        //     element: (
        //         <ProtectedRoute>
        //             <Checkout />
        //       </ProtectedRoute>
        //   )  
        // },

      //  Auth
      {
        path: "account",
        children: [
          { path: "login", element: <Auth type="login" /> },
          { path: "register", element: <Auth type="register" /> },
        ],
      },

      //  Customer Profile (Protected)
      {
        path: "my-profile",
        element: (
          <ProtectedRoute>
            <ProfileLayout />
          </ProtectedRoute>
        ),
        children: [
        //   { index: true, element: <UserProfile /> },
        //   { path: "orders", element: <Orders /> },
        //   { path: "favourites", element: <Favourites /> },
        //   { path: "address", element: <Address /> },
        //   { path: "payment", element: <Payment /> },
        //   { path: "logout", element: <Logout /> },
        ],
      },

    //   //  Checkout
    //   {
    //     path: "checkout",
    //     element: (
    //       <ProtectedRoute>
    //         <Payment />
    //       </ProtectedRoute>
    //     ),
    //   },
    ],
  },

  //  Restaurant Partner Routes
  {
    path: "restaurant",
    element: (
      <RoleRoute role="RESTAURANT">
        {/* <AdminLayout /> */}
      </RoleRoute>
    ),
    children: [
    //   { index: true, element: <RestaurantDashboard /> },
    //   { path: "orders", element: <RestaurantOrders /> },
    //   { path: "menu", element: <RestaurantMenu /> },
    //   { path: "add-menu", element: <CreateRestaurantForm /> },
    ],
    },
  
    // DELIVERY PARTNER ROUTES
  
    // {
    //     path: "/delivery",
    //     element: (
    //         <RoleRoute role="DELIVERY">
    //             <DeliveryLayout />
    //         </RoleRoute>
    //     ),
    //     children: [
    //         { index: true, element: <DeliveryDashboard /> },
    //         { path: "orders/active", element: <ActiveDeliveries /> },
    //         { path: "orders/completed", element: <CompletedDeliveries /> },
    //         { path: "earnings", element: <DeliverEarnings /> },
    //         { path: "profile", element: <DeliveryProfile /> },
    //         { path: "availability", element: <DeliveryAvailability /> }
    //     ],
    // },

    // SUPER ADMIN
    // {
    //     path: "/admin",
    //     element: (
    //         <RoleRoute role="ADMIN">
    //             <SuperAdminLayout />
    //         </RoleRoute>
    //     ),
    //     children: [
    //         {index: true, element: <AdminDashboard />},
    //         {path : "restaurants", element: <AdminRestaurants />},
    //         { path: "delivery-partners", element: <AdminDeliveryPartners /> },
    //         { path: "orders", element: <AdminOrders /> },
    //         { path: "users", element: <AdminUsers /> },
    //         { path: "categories", element: <AdminCategories /> },
    //         { path: "payouts", element: <AdminPayouts /> },
    //         { path: "reports", element: <AdminReports /> },
    //     ]
  // }
    // ✅ GLOBAL 404
    { path: "*", element: <NotFound /> },
]);

export default function AppRouter() {
  return <RouterProvider router={rootRouter} />;
}
