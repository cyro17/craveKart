import { Button, Card } from '@mui/material'
import React from 'react'

export default function OrderCard() {
  return (
      <Card>
          <div className='flex justify-between items-center p-5 h-2/3'>
              <div className='flex items-center space-x-5'>
                  <img
                      src="https://cdn.britannica.com/35/225835-050-A5CC289A/Indian-one-pot-meal-for-party.jpg?w=300"
                      className='h-16 w-16'
                      alt=""
                  />
              </div>
              <div>
                  <p>Biryani</p>
                  <p>Rs 399</p>
              </div>
              <div>
                  <Button disabled className='cursor-not-allowed'>
                      completed
                  </Button>
              </div>
          </div>
    </Card>
  ) }
