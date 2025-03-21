import { CssBaseline, ThemeProvider } from '@mui/material';
import './App.css';
import Navbar from './component/navbar/Navbar.jsx';
import { darkTheme } from './Theme/DarkTheme';
import Home from './component/Home/Home.jsx';
import RestaurantDetails from './component/restaurant/RestaurantDetails.jsx';

function App() {
  return (
    <ThemeProvider theme={darkTheme}>
      <CssBaseline />
      <Navbar />
      {/* <Home /> */}
      <RestaurantDetails />

    </ThemeProvider>
  );
}

export default App;
