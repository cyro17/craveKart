import { CssBaseline, ThemeProvider } from '@mui/material';
import './App.css';
import { darkTheme } from './Theme/DarkTheme.js';
import Orders from './component/profile/Orders.jsx';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Home from './component/Home/Home';
import Cart from './component/cart/Cart';
import Favourites from './component/profile/Favourites';
import Address from './component/profile/Address';
import Events from './component/profile/Events';
import Layout from './Layout';
import Profile from './component/profile/Profile.jsx';
import { Logout } from '@mui/icons-material';
import Auth from './component/Auth/Auth.jsx';
import UserProfile from "./component/profile/UserProfile"

const router = createBrowserRouter([
  {
    path: '/',
    element: <Layout />,
    children: [
      { index: true, element: <Home />},
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
          { index: true, element: <UserProfile />},
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
  return (
    <ThemeProvider theme={darkTheme}>
      <CssBaseline />
      <RouterProvider router={router}/>
    </ThemeProvider>
  );
}

