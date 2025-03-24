import { CssBaseline, ThemeProvider } from '@mui/material';
import './App.css';
import Navbar from './component/navbar/Navbar.jsx';
import { darkTheme } from './Theme/DarkTheme';
import Home from './component/Home/Home.jsx';
import RestaurantDetails from './component/restaurant/RestaurantDetails.jsx';
import Cart from './component/cart/Cart.jsx';
import Profile from './component/profile/Profile.jsx';

function App() {
  return (
    <ThemeProvider theme={darkTheme}>
      <CssBaseline />
      <Navbar />
      {/* <Home /> */}
      {/* <RestaurantDetails /> */}
      {/* <Cart /> */}
      <Profile />

    </ThemeProvider>
  );
}

export default App;
