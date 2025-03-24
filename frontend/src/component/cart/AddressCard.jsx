
import { Button, Card } from '@mui/material';
import React from 'react';
import HomeIcon from '@mui/icons-material/Home';


export default function AddressCard({item, showButton, handleSelectAddress}) {
    function createOrderUsingSelectedAddress() {
        console.log('Order created successfully');  
    }

  return (
      <Card className='flex gap-5 w-64 p-5 m-5'>
          <HomeIcon />
          <div className='space-y-3 text-gray-500 '>
              <div className='font-semibold text-lg text-white'>Home</div>
              <p>
                  Flat no-12, Gokuldham Society, Near Water Tank,
                  Goregaon East, Mumbai, Maharashtra, 400063
              </p>
              { showButton &&
                <Button variant="outlined" fullWidth onClick={() => handleSelectAddress()}>
                    Select
                </Button>
              }
          </div>
      </Card>
  )
}
