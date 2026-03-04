const { createTheme } = require("@mui/material");


export const darkTheme = createTheme({
    palette: {
        mode: "dark",
        primary: {
            main: '#e91e63',
        },
        secondary: {
            main: '#5A20CB'
        },
        background: {
            default: "#f8f9fa",
            paper: "#ffffff",
        },
        text: {
            primary: '#111827',
            secondary: "#4b5563",
        },
        greyButton: {
            main: "#f3f4f6",
            dark: "#e5e7eb",
            contrastText: "#111827",
        },
    }
});