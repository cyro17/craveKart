import { createBrowserRouter,  RouterProvider } from "react-router-dom";
import NotFound from "../component/NotFound/NotFound.jsx";
import Home from "../component/Home/Home.jsx";
import Cart from "../component/cart/Cart.jsx"; 
import Auth from "../component/Auth/Auth.jsx";
import Profile from "../component/profile/Profile.jsx";
import UserProfile from "../component/profile/UserProfile.jsx";
import Orders from "../component/profile/Orders.jsx";
import Favourites from "../component/profile/Favourites.jsx";
import Address from "../component/profile/Address.jsx";
import Events from "../component/profile/Address.jsx";
import { Logout } from "@mui/icons-material";
import RootLayout from "./Layout.jsx";
import Payment from "../component/profile/Payment.jsx";
import RestaurantDashboard from "../Admin/Dashboard/RestaurantDashboard.jsx";
import RestaurantOrder from "../Admin/Orders/RestaurantOrder.jsx";
import RestaurantMenu from "../Admin/Food/RestaurantMenu.jsx";
import CreateRestaurantForm from "../Admin/AddRestaurants/CreateRestaurant.jsx";
import Ingredients from "../Admin/Ingredients/Ingredients.jsx";
import Category from "../Admin/Category/Category.jsx";
import Details from "../Admin/Details/Details.jsx";
import AdminLayout from "./AdminLayout.jsx";

const rootRouter = 
    createBrowserRouter([
        {
            path: '/',
            element: <RootLayout />,
            errorElement: <NotFound />,
            children: [
                {
                    index: true,
                    element: <Home />
                },{
                    path: "cart",
                    element: <Cart />
                },{
                    path: "account", element: <Home />,
                    children: [
                        { path: "login", element: <Auth /> },
                        { path: "register", element: <Auth /> },
                    ]
                }, {
                    path: "my-profile",
                    element: <Profile />,
                    children: [
                        { index: true, element: <UserProfile /> },
                        { path: "order", element: <Orders /> },
                        { path: "favourite", element: <Favourites /> },
                        { path: "payment", element: <Payment /> },
                        { path: "address", element: <Address /> },
                        { path: "events", element: <Events /> },
                        { path: "logout", element: <Logout /> },
                    ]
                }
            ]
        },
        {
            path: "admin/restaurants",
            element:  <AdminLayout/>, 
            children: [
                {
                    index: true, 
                    element: <RestaurantDashboard />, 
                },{
                    path: "orders", 
                    element: <RestaurantOrder/>
                }, {
                    path: "menu", 
                    element: <RestaurantMenu/>
                }, {
                    path: "add-menu", 
                    element: <CreateRestaurantForm/>
                }, {
                    path: "event", 
                    element: <Home/>
                }, {
                    path: "ingredients", 
                    element: <Ingredients/>
                }, {
                    path: "category", 
                    element: <Category/>
                }, {
                    path: "details", 
                    element: <Details/>
                }
            ]
        }, 

    ]);

export default function AppRouter(){
    return <RouterProvider router={rootRouter}/>
}