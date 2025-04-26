import React from 'react';

import SearchIcon from '@mui/icons-material/Search';
import PersonIcon from '@mui/icons-material/Person';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import IconButton from '@mui/material/IconButton';
import { Avatar, Badge } from '@mui/material';
import './Navbar.css';
import { useNavigate } from 'react-router-dom';
import { pink } from '@mui/material/colors';
import { useSelector } from 'react-redux';


export default function Navbar() {
    const navigate = useNavigate();
    const { auth } = useSelector(store => store);
    // console.log("auth", auth);

    const handleClick = () => {
        navigate("/");
    }

    const handleAvatarClick = () => {
        if (auth.user?.role === "ROLE_CUSTOMER")
            navigate("/my-profile");
        else
            navigate("/admin/restaurant");
    }

    return (
        <div className='navbar sticky top-[0] w-full  px-5 z-50 py-[.8rem]
             bg-[#e91e63] lg:px-20 flex justify-between' >
            
            <div className='lg:mr-10 cursor-pointer flex items-center space-x-4'>
                <div className='logo font-semibold text-grey-300 text-2xl' onClick={handleClick}>
                    CraveKart
                </div>
            </div>

            <div className='flex space-x-4'>
                <IconButton>
                    <SearchIcon sx={{ fontSize: "1.5rem" }} />
                </IconButton>

                <IconButton className=''>
                    { auth.user ? (
                        <Avatar sx={{ bgcolor: "white", color: pink.A400 }}
                        onClick= {handleAvatarClick}>
                                {auth?.user?.fullName[0]}
                            </Avatar>)
                        : (
                            <IconButton onClick={() => navigate("/account/login")}>
                            <PersonIcon/>
                            </IconButton>
                        )
                    }
                </IconButton>

                <IconButton className=''>
                    <Badge badgeContent={4} color="secondary">
                        <ShoppingCartIcon sx={{ fontSize: "1.5rem" }} />
                    </Badge>
                </IconButton>
            </div>

        </div>
    )
} 
