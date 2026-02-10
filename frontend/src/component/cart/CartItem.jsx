
import { Box, IconButton, Typography } from '@mui/material';
import React from 'react';
import AddIcon from '@mui/icons-material/Add';
import RemoveIcon from '@mui/icons-material/Remove';
import DeleteIcon from '@mui/icons-material/Delete';


export default function CartItem({item}) {
  return (
      <>
          <Box display="flex" gap={2} py-3 borderBottom={0.0005} borderColor="grey.100" >
               {/* Image */}
              <Box component="img" src={item.image} alt={item.name} margin={1} sx={{
                  width: 80, 
                  height: 80,
                  borderRadius: 2, 
                  objectFit: 'cover'
              }} />

              {/* Details  */}
              <Box flex={1} >
                <Typography>{item.foodName}</Typography>
                  {/* <Typography variant="body2" color="text.secondary">{item.restaurant}</Typography> */}
                  <Box display="flex" justifyContent="space-between" alignItems="center" >
                      <Box display="flex" alignItems="center" mt={1}>
                          <IconButton size='small'><RemoveIcon /></IconButton>
                          <Typography mx={1}>{item.quantity}</Typography>
                          <IconButton><AddIcon/></IconButton>
                      </Box>

                      <Box>
                          <Typography fontWeight={600} >
                          ₹{item.totalPrice * item.quantity}
                          </Typography>

                        <IconButton color="error">
                            <DeleteIcon />
                        </IconButton>
                      </Box>

                  </Box>
                        
                      
              </Box>
          </Box>
      </>
  )
}
