import { Card, Chip, IconButton } from '@mui/material';
import React from 'react';
import FavoriteIcon from '@mui/icons-material/Favorite';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';

export default function RestaurantCard() {
  return (
    <Card className='w-[18rem] mb-5'>
      <div className={`${true? 'cursor-pointer' : 'cursore-not-allowed'} relative`}>
        <img className='w-full h-[10rem] rounded-t-md'
          src="https://images.unsplash.com/photo-1582228096960-7f5d2ec4aa8e?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8aW5kaWFuJTIwcmVzdGF1cmFudHxlbnwwfHwwfHx8MA%3D%3D"
          alt="indian fast food" />
        <Chip size='small'
          className='absolute top-2 left-2'
          color={true ? "success" : "error"}
          label={true ? "Open" : "Closed"} />
      </div>

      <div className='p-4 textPart lg:flex w-full justify-between'>
        <div className='space-y-1'>
          <p className='font-semibold text-lg'>Indian Fast Food</p>
          <p className='text-gray-500 text-sm'>
            Crave it all ? Dive into our global fla...
          </p>
        </div>

        <div>
          <IconButton>
            {true? <FavoriteIcon/> : <FavoriteBorderIcon/>}
          </IconButton>
        </div>
      </div>

    </Card>
  )
}
