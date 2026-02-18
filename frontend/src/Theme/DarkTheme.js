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
            default: "#0D0D0D",
            paper: "#000000",
        },
        text: {
            primary: '#000000'
        },
        greyButton: {
            main: "#2a2a2a",
            dark: "#3a3a3a",
            contrastText: "#ffffff",
        },
    }
});