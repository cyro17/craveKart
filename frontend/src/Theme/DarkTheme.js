const { createTheme } = require("@mui/material");
const { dark } = require("@mui/material/styles/createPalette");


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
            main: "#000000",
            default: '#0D0D0D'
        },
        text: {
            primary: '#FFFFFF'
        },
    }
});