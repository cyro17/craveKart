import React from 'react';

import SearchIcon from '@mui/icons-material/Search';
import PersonIcon from '@mui/icons-material/Person';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import IconButton from '@mui/material/IconButton';
import { Avatar, Badge, Menu, MenuItem } from '@mui/material';
import './Navbar.css';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { logout } from "../../State/Authentication/actions.js";


export default function Navbar() {
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const { auth } = useSelector(store => store);
    console.log("auth", auth);


    const [anchorEl, setAnchorEl] = React.useState(null);
    const open = Boolean(anchorEl);

    const handleOpenMenu = (event) => {
        setAnchorEl(event.currentTarget);
    }

    const handleCloseMenu = (event) => {
        setAnchorEl(null);
    }

    const navigateToCart = () => {
        navigate("/cart");
      };

    const navigateToProfile = (evene) => {
        auth.user?.role === "ADMIN" ||
            auth.user?.role === "RESTAURANT_PARTNER" ?
            navigate("/admin/restaurants") : navigate("/my-profile");
    }

    const handleCloseAuthModel = () => {
        navigate("/");
    }

    const navigateToHome = () => {
        navigate("/");
    }

    const handleLogout = () => {
        dispatch(logout());
        handleCloseMenu();
    }


    return (
        <div className='navbar sticky top-[0] w-full  px-5 z-50 py-[.8rem]
             bg-[#e91e63]  h-[60px] lg:px-20 flex justify-between' >
            
            <div className='lg:mr-10 cursor-pointer flex items-center space-x-4'>
                <div className='logo font-bold text-grey-300 text-2xl' >
                    CraveKart
                </div>
            </div>

            <div className='flex items-center space-x-2 lg:space-x-10'>
                <div>
                    <IconButton onClick={()=>navigate("/search")}>
                        <SearchIcon sx={{ fontSize: "1.5rem" }} />
                    </IconButton>
                </div>
                
                <div className="flex items-center space-x-2">    
                    {
                        auth.user?.firstName ? (
                            <span
                                id='demo-positioned-button'
                                aria-controls={open ? "demo-positioned-menu" : undefined}
                                aria-haspopup="true"
                                aria-expanded={open ? "true" : undefined}
                                onClick={
                                    auth.user?.role !== "ADMIN" ?
                                    handleOpenMenu :
                                    navigateToProfile
                                }
                                className='font-semibold cursor-pointer'
                            >
                                <Avatar >
                                    { auth.user.firstName[0].toUpperCase()}
                                </Avatar>
                            </span>
                        ) : (
                                <IconButton onClick={()=>navigate("/account/login")}>
                                    <PersonIcon sx={{fontSize: "2rem"}}/>
                                </IconButton>
                        )
                    }
                    <Menu
                        id="basic-menu"
                        anchorEl={anchorEl}
                        open={open}
                        onClose={handleCloseMenu}
                        slotProps={{
                            paper: {
                              sx: {
                                width: 220, 
                                padding: 1,
                                backgroundColor: 'transparent', 
                                boxShadow: 'none', 
                                backdropFilter: 'none'
                              },
                            },
                          }}
                    >
                        <MenuItem
                        onClick={() =>
                            auth.user?.role === "ROLE_ADMIN"
                            ? navigate("/admin")
                            : navigate("/super-admin")
                        }
                        >
                            Profile
                        </MenuItem>
                        <MenuItem onClick={handleLogout}>
                            Logout
                        </MenuItem>
                    </Menu>     
                </div>
                        
                    <IconButton className=''>
                        <Badge badgeContent={4} color="secondary">
                            <ShoppingCartIcon sx={{ fontSize: "1.5rem" }} />
                        </Badge>
                        </IconButton>
                
            </div>

        </div>
    )
} 
