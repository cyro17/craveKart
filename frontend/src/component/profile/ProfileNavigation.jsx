import React from 'react';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ShoppingBagIcon from '@mui/icons-material/ShoppingBag';
import LogoutIcon from '@mui/icons-material/Logout';
import NotificationsIcon from '@mui/icons-material/Notifications';
import PaymentIcon from '@mui/icons-material/Payment';
import BusinessIcon from '@mui/icons-material/Business';
import {  Drawer, useMediaQuery } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { logout } from '../../State/Admin/Authentication/actions';
import { useDispatch } from 'react-redux';

const menu = [
    {
        title: "Order", 
        icon: <ShoppingBagIcon />
    }, 
    {
        title: "Favourite", 
        icon: <FavoriteIcon />
    }, 
    {
        title: "Address", 
        icon: < BusinessIcon/>
    },
    { 
        title: "Payments", 
        icon: <PaymentIcon />
    },
    {
        title: "Events", 
        icon: <NotificationsIcon />
    }, 
    {
        title: "Logout", 
        icon: <LogoutIcon /> 
    }
]

export default function ProfileNavigation({open, handleClose}) {
    const isSmallScreen = useMediaQuery("(max-width: 1080)");
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const handleLogout = () => {
        dispatch(logout());
    }

    const handleNavigate = (item) => {
        navigate(`/my-profile/${item.title.toLowerCase()}`);
        if (item.title === "Logout") {
            handleLogout();
            navigate("/");
        }
    }

  return (
      <div>
          <Drawer anchor='left'
              open={isSmallScreen ? open : true}
              onClose={handleClose}
              variant={isSmallScreen ? "temporary" : "permanent"}
              sx={{ zIndex: 1 }} >
              <div className='w-[50vw] lg:w-[20vw] h-[100vh] flex flex-col justify-center text-xl 
                                gap-8 pt-16 '>
                  {
                      menu.map((item, index) => (
                          <div onClick={() => handleNavigate(item)}
                              className='px-5 flex items-center space-x-5 cursor-pointer h-20'>
                                    {item.icon}
                                    <span>{item.title}</span>
                          </div>
                      ))
                  }
                </div>
              
          </Drawer>
    </div>
  )
}
