import { CssBaseline, ThemeProvider } from '@mui/material';
import './App.css';
import { darkTheme } from './Theme/DarkTheme.js';
import Orders from './component/profile/Orders.jsx';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Home from './component/Home/Home.jsx';
import Cart from './component/cart/Cart.jsx';
import Favourites from './component/profile/Favourites.jsx';
import Address from './component/profile/Address.jsx';
import Events from './component/profile/Events.jsx';
import Layout from './Layout.jsx';
import Profile from './component/profile/Profile.jsx';
import { Logout } from '@mui/icons-material';
import Auth from './component/Auth/Auth.jsx';
import UserProfile from "./component/profile/UserProfile.jsx"
import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from 'react';
import { getUser } from './State/Authentication/actions.js';
import { getAllRestaurantRequest } from './State/Customers/Restaurant/restaurantSlice.js';

const router = createBrowserRouter([
  {
    path: '/',
    element: <Layout />,
    children: [
      { index: true, element: <Home /> },
      { path: "cart", element: <Cart /> },
      {
        path: "account", element: <Home />,
        children: [
          { path: "login", element: <Auth /> },
          { path: "register", element: <Auth /> },

        ]
      },
      {
        path: "my-profile",
        element: <Profile />,
        children: [
          { index: true, element: <UserProfile /> },
          { path: "order", element: <Orders /> },
          { path: "favourite", element: <Favourites /> },
          { path: "address", element: <Address /> },
          { path: "events", element: <Events /> },
          { path: "logout", element: <Logout /> },
        ]
      }
    ]
  }
]);

export default function App() {
  const dispatch = useDispatch();
  const { auth } = useSelector(state => state);
  // const jwt = localStorage.setItem("jwt", auth.jwt);
  const jwt = localStorage.getItem("jwt")
  console.log("auth", auth);

  useEffect(() => {
    const jwt = localStorage.getItem("jwt");
    console.log("jwt on mount", jwt);
    if (jwt) {
      dispatch(getUser(jwt));
      dispatch(getAllRestaurantRequest(jwt));
    }
  }, [auth.jwt]);

  useEffect(() => {
    if (auth.user?.role === "ROLE_RESTAURANT_OWNER") {

    }
  }, [auth.user])

  return (
    <ThemeProvider theme={darkTheme}>
      <CssBaseline />
      <RouterProvider router={router} />
    </ThemeProvider>
  );
}

