import { Box } from '@mui/material';
import React from 'react'
import { Outlet } from 'react-router-dom';

function RestaurantLayout() {
    return (
        <Box className="restaurant-layout">
        {/* Header / banner / tabs */}
        Restaurant page
          <Outlet />
        </Box>
      );
}

export default RestaurantLayout