import { Card, CardActions, CardContent, CardMedia, Typography } from '@mui/material'
import React from 'react'

export default function EventCard() {
  return (
      <div>
          <Card sx={{width: 345}}>
              <CardMedia
                  
                  sx={{height: 345}}
                  image='https://plus.unsplash.com/premium_photo-1686538381343-ff6de76c8712?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8Zm9vZCUyMGZlc3RpdmFsfGVufDB8fDB8fHww' />
              <CardContent>
                  <Typography variant='h5' >
                      Indian Fast Food 
                  </Typography>

                  <Typography variant='body2' >
                      50% off on you first order
                  </Typography>
                  <div className='py-2 space-y-2'>
                      <p>
                          {"Mumbai"}
                          <p className='text-sm text-blue-500 '>
                              March 31st, 2025 12:00 AM 
                          </p>
                          <p className='text-sm text-red-500 '>
                              March 31st, 2025 10:00 PM 
                          </p>
                      </p>
                  </div>
              </CardContent>

              <CardActions>
                  
              </CardActions>
          </Card>
    </div>
  )
}
