import { Box, Modal } from '@mui/material'
import React from 'react'
import { useLocation, useNavigate } from 'react-router-dom'
import { style } from '../cart/Cart';
import LoginForm from "./LoginForm"
import RegisterForm from "./RegisterForm"

export default function Auth() {
    const location = useLocation();
    const navigate = useNavigate();

    const handleOnClose = () => {
        navigate("/");
    }
    return (
      <div>
            <Modal open={
                location.pathname === "/account/register" ||
                location.pathname === "/account/login"
            } onClose={handleOnClose} >
                <Box sx={style}>
                    {
                        location.pathname === "/account/register" ? 
                            <RegisterForm/> : <LoginForm/>
                    }
                </Box>
          
          </Modal>    
      </div>
  )
}
