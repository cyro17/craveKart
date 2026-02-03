import { CssBaseline, ThemeProvider } from '@mui/material';
import './App.css';
import { darkTheme } from './Theme/DarkTheme.js';
import AppRouter from './Routers/AppRouter.jsx';
import SideBarProvider from './State/Store/SideBarContext/SideBarProvider.jsx';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from 'react';
// import { getUser } from './State/Authentication/actions.js';
import { getAllRestaurant } from './State/Customers/Restaurant/actions.js';

export default function App() {

  const { auth } = useSelector(store => store);
  const jwt = localStorage.getItem("jwt");
  const dispatch = useDispatch();

  useEffect(() => {
    if (jwt) {
      // dispatch(getUser(jwt));
      dispatch(getAllRestaurant(jwt));
    }
  }, [auth.jwt])

  // useEffect(() => {
  //   if (auth.user?.role === "ROLE_RESTAURANT_OWNER") {
  //     dispatch(getRestaurantByUserId(auth.jwt || jwt));
  //   }
  // }, [auth.user]);

  return (
    <ThemeProvider theme={darkTheme}>
      <SideBarProvider>
        <CssBaseline />
        <AppRouter />
      </SideBarProvider >
    </ThemeProvider>
  );
}

