
import React from 'react';
import SearchIcon from '@mui/icons-material/Search';
import PersonIcon from '@mui/icons-material/Person';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import IconButton from '@mui/material/IconButton';
import { Badge } from '@mui/material';
import './Navbar.css';


export default function Navbar() {
    return (
        <div className='navbar sticky top-[0] w-full  px-5 z-50 py-[.8rem] bg-[#e91e63] lg:px-20 flex justify-between' >

            <div className='lg:mr-10 cursor-pointer flex items-center space-x-4'>
                <div className='logo font-semibold text-grey-300 text-2xl'>
                    CraveKart
                </div>
            </div>

            <div className='flex space-x-4'>
                <IconButton>
                    <SearchIcon sx={{ fontSize: "1.5rem" }} />
                </IconButton>

                <IconButton className=''>
                    <PersonIcon sx={{ fontSize: "1.5rem" }} >R</PersonIcon>
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
