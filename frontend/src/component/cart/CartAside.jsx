import { Box, Drawer, Typography } from '@mui/material';
import React from 'react'
import CartItem from './CartItem';

export default function CartAside({ open, onClose }) {
    const cartItems = [
        {
          id: 1,
          foodName: "Burger Deluxe",
          restaurant: "McDonald's",
          totalPrice: 199,
          quantity: 2,
          image:
            "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8YnVyZ2VyfGVufDB8MHwwfHx8Mg%3D%3D",
        },
        {
          id: 2,
          name: "Veg Pizza",
          restaurant: "Domino's",
          price: 349,
          quantity: 1,
          image:
            "https://images.unsplash.com/photo-1513104890138-7c749659a591?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8cGl6emF8ZW58MHwwfDB8fHwy",
        },
      ];
    // const cartItems = null;
    return (
      
        <Drawer anchor='right' open={open} onClose={onClose}
            PaperProps={{
                sx:
                {
                    width: {
                        xs: "100vw",   // mobile
                        sm: "60vw",
                        md: "30vw",
                        lg: "30vw",    // desktop
                    },
                    padding: 2, 
                    background: "rgba(100, 100, 100, 0.1)",
                    backdropFilter: "blur(20px)",
                    WebkitBackdropFilter: "blur(20px)",

                    borderLeft: "1px solid rgba(255,255,255,0.4)",
                    boxShadow: "0 8px 32px rgba(0,0,0,0.2)",
                }
            }}
            BackdropProps={{
                sx: {
                    backdropFilter: 'blur(4px)',
                    backgroundColor: "rgba(0, 0, 0, 0.1)"
                }
            }}
        >

            <div className=''>
                <Typography fontWeight={600}>
                    Your Cart
                </Typography>
                
                <Box mt={2} borderBottom={0.1}>
                    {cartItems.map((item) => (
                        <CartItem key={item.id} item={item} />
                    ))}
                    
</Box>
            </div>

        </Drawer>
  )
}
