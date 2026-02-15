import { Box, Drawer, Fade, IconButton, Typography } from "@mui/material";
import React from "react";
import CloseIcon from "@mui/icons-material/Close";
import { useNavigate } from "react-router-dom";
import LoginPage from "./LoginPage";
import RegisterPage from "./RegisterPage";

export default function AuthDrawer({ open, onClose, mode, setMode }) {
    const navigate = useNavigate();
    return (
        <Drawer
            anchor="right"
            open={open}
            onClose={onClose}
            transitionDuration={400}
            ModalProps={{
                BackdropProps: {
                    sx: {
                        backdropFilter: "blur(6px)",
                        backgroundColor: "rgba(0, 0, 0, 0.5)",
                    },
                },
            }}
            PaperProps={{
                sx: {
                    width: {
                        xs: "100vw", // mobile
                        sm: "60vw",
                        md: "30vw",
                        lg: "35vw", // desktop
                    },
                    padding: 2,
                    background: "rgba(255, 255, 255, 0.6)",
                    color: "#1a1a1a",
                    backdropFilter: "blur(20px)",
                    WebkitBackdropFilter: "blur(20px)",

                    borderLeft: "1px solid rgba(255,255,255,0.4)",
                    boxShadow: "0 8px 32px rgba(0,0,0,0.2)",
                },
            }}
            BackdropProps={{
                sx: {
                    backdropFilter: "blur(4px)",
                    backgroundColor: "rgba(0, 0, 0, 0.1)",
                },
            }}
        >
            {/* Header */}
            <Box className="flex flex-col text-black">
                <Box
                    display="flex"
                    justifyContent="space-between"
                    alignItems="center"
                    mb={4}
                >
                    <Typography variant="h6" fontWeight="bold" color="black">
                        {mode === "login" ? "Login" : "Sign Up"}
                    </Typography>

                    <IconButton onClick={onClose}>
                        <CloseIcon />
                    </IconButton>
                </Box>

                <Box className="flex flex-col ">
                    <Fade in={mode === "login"} timeout={300} unmountOnExit>
                        <Box>
                            {mode === "login" && (
                                <LoginPage
                                    onSuccess={onClose}
                                    switchMode={() => setMode("register")}
                                />
                            )}
                        </Box>
                    </Fade>

                    <Fade in={mode === "register"} timeout={300} unmountOnExit>
                        <Box>
                            {mode === "register" && (
                                <RegisterPage
                                    onSuccess={onClose}
                                    switchMode={() => setMode("login")}
                                />
                            )}
                        </Box>
                    </Fade>
                </Box>
            </Box>
        </Drawer>
    );
}
