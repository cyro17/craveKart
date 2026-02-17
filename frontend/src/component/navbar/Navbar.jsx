import React, { useEffect, useRef, useState } from "react";

import SearchIcon from "@mui/icons-material/Search";
import PersonIcon from "@mui/icons-material/Person";
import ShoppingCartIcon from "@mui/icons-material/ShoppingCart";
import IconButton from "@mui/material/IconButton";
import { Avatar, Badge, InputBase, Menu, MenuItem } from "@mui/material";
import "./Navbar.css";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { logout } from "../../State/Authentication/authSlice";

export default function Navbar({
    onCartOpen,
    openAuthLogin,
    openAuthRegister,
}) {
    const navigate = useNavigate();
    const [anchorEl, setAnchorEl] = React.useState(null);
    const open = Boolean(anchorEl);

    const searchRef = useRef(null);
    const [searchOpen, setSearchOpen] = React.useState(false);
    const [searchQuery, setSearchQuery] = React.useState("");

    const auth = useSelector((state) => state.auth);
    const { items } = useSelector((state) => state.cart);
    // console.log(auth);

    const handleSearchOpen = () => {
        setSearchOpen(true);
        if (searchRef.current) {
            searchRef.current.focus();
        }
    };

    const handleSearchClose = () => {
        setSearchOpen(false);
        setSearchQuery("");
    };

    const dispatch = useDispatch();

    useEffect(() => {
        function handleClickOutside(event) {
            if (
                searchRef.current &&
                !searchRef.current.contains(event.target)
            ) {
                handleSearchClose();
            }
        }

        if (searchOpen) {
            document.addEventListener("mousedown", handleClickOutside);
        }
        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, [searchOpen]);

    const handleSearchSubmit = (e) => {
        if (e.key === "Enter") {
            navigate("/");
            handleSearchClose();
        }
    };

    const handleOpenMenu = (event) => {
        setAnchorEl(event.currentTarget);
    };
    const handleCloseMenu = () => {
        setAnchorEl(null);
    };
    const navigateToProfile = () => {
        navigate("/profile");
    };
    const handleLogout = () => {
        dispatch(logout());
        handleCloseMenu();
        navigate("/");
    };

    return (
        <header
            className="navbar sticky top-[0] w-full  px-6 z-50 py-[.8rem]
             bg-gradient-to-r from-rose-600 to-pink-600  h-[70px] lg:px-20 flex justify-between"
        >
            {/* Left Logo */}
            <div
                onClick={() => navigate("/")}
                className="lg:mr-10 cursor-pointer flex items-center space-x-4"
            >
                <div className="logo font-extrabold text-grey-300 text-2xl tracking-wider">
                    Crave<span className="text-yellow-300">Kart</span>
                </div>
            </div>

            {/* RIGHT Actions  */}
            <div className="flex items-center space-x-2 lg:space-x-10 p-2">
                {/* Search Bar */}
                <div
                    ref={searchRef}
                    className={` text-black rounded-md overflow-hidden flex items-center ease-in-out 
                        transition-all transition-duration-300
                        ${searchOpen ? "w-full px-3 py-1" : "w-auto"}`}
                >
                    {searchOpen ? (
                        <InputBase
                            className="text-black bg-stone-300  rounded-md px-2 py-1 w-full"
                            autoFocus
                            placeholder="Search..."
                            value={searchQuery}
                            onChange={(e) => setSearchQuery(e.target.value)}
                            onKeyDown={handleSearchSubmit}
                            sx={{
                                ml: 1,
                                flex: 1,
                                fontSize: "1.2rem",
                                color: "black",
                                "& input": {
                                    color: "black",
                                },
                                "& input::placeholder": {
                                    color: "#555",
                                    opacity: 1,
                                },
                            }}
                        />
                    ) : (
                        <IconButton
                            className=" hover:scale-110 transition-transform"
                            onClick={handleSearchOpen}
                            sx={{ p: 1 }}
                        >
                            <SearchIcon sx={{ fontSize: "1.5rem" }} />
                        </IconButton>
                    )}
                </div>

                <div className="flex items-center space-x-2">
                    {auth.user?.username ? (
                        <span
                            id="demo-positioned-button"
                            aria-controls={
                                open ? "demo-positioned-menu" : undefined
                            }
                            aria-haspopup="true"
                            aria-expanded={open ? "true" : undefined}
                            onClick={
                                auth.user.user?.role !== "ADMIN"
                                    ? handleOpenMenu
                                    : navigateToProfile
                            }
                            className="font-semibold cursor-pointer"
                        >
                            <Avatar>
                                {auth.user.username[0].toUpperCase()}
                            </Avatar>
                        </span>
                    ) : (
                        <IconButton onClick={openAuthLogin}>
                            <PersonIcon
                                className="hover:scale-110 transition-transform"
                                sx={{ fontSize: "2rem" }}
                            />
                        </IconButton>
                    )}

                    <Menu
                        id="user-menu"
                        anchorEl={anchorEl}
                        open={open}
                        onClose={handleCloseMenu}
                        anchorOrigin={{
                            vertical: "bottom",
                            horizontal: "right",
                        }}
                        slotProps={{
                            paper: {
                                sx: {
                                    width: 220,
                                    padding: 1,
                                    // backgroundColor: 'transparent',
                                    // boxShadow: 'none',
                                    // backdropFilter: 'none'
                                },
                            },
                        }}
                    >
                        <MenuItem
                            onClick={() =>
                                auth.user?.role === "ADMIN"
                                    ? navigate("/admin")
                                    : navigate("/super-admin")
                            }
                        >
                            Profile
                        </MenuItem>
                        <MenuItem onClick={() => navigate("/profile/orders")}>
                            Orders
                        </MenuItem>
                        <MenuItem onClick={handleLogout}>Logout</MenuItem>
                    </Menu>
                    {/* <AccountMenu/> */}
                </div>

                <IconButton
                    className="hover:scale-110 transition-transform"
                    onClick={onCartOpen}
                >
                    <Badge badgeContent={items.length} color="secondary">
                        <ShoppingCartIcon sx={{ fontSize: "1.5rem" }} />
                    </Badge>
                </IconButton>
            </div>
        </header>
    );
}
