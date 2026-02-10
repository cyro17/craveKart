import { CssBaseline, ThemeProvider } from '@mui/material';
import './App.css';
import { darkTheme } from './Theme/DarkTheme.js';
import AppRouter from './routes/AppRouter.jsx';

export default function App() {


  return (
    <ThemeProvider theme={darkTheme}>
      {/* <SideBarProvider> */}
      <CssBaseline />
      <AppRouter />
      {/* </SideBarProvider > */}
    </ThemeProvider>
  );
}

