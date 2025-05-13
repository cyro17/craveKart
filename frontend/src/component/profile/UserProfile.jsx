import React from 'react';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import { Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { logout } from '../../State/Authentication/actions';

export default function UserProfile() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  
  const handleLogout = () => {
    navigate("/");
    dispatch(logout());
  }
  return (
    <div className='min-h-[80vh] flex flex-col justify-center items-center text-center'>
      <div className='flex flex-col items-center justify-center  '>
        <AccountCircleIcon sx={{fontSize: "9rem"}}/>
        <h1 className='py-5 text-2xl font-semibold'>Cyro</h1>
        <p>Email: cyro@dev.com</p>
        <Button
          onClick={handleLogout}
          variant='contained'
          sx={{ margin: "2rem 0rem" }}
        >
          Logout
        </Button>
      </div>
    </div>
  )
}
