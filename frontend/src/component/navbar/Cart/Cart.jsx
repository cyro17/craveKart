import { Drawer, Typography } from '@mui/material';
import React from 'react'

export default function Cart({ open, onClose }) {
    // const cartItems = null;
    return (
      
        <Drawer anchor='right' open={open} onClose={onClose}
            paperprops={{
                sx:
                {
                    width: { xs: '100%', sm: '400px' }, 
                    padding: 2
                }
            }}
        >

            <div className=''>
                <Typography>
                    Your Cart
                </Typography>
                
            </div>

        </Drawer>
  )
}
