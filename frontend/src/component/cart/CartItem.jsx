import { Chip, IconButton } from '@mui/material';
import React from 'react';
import RemoveCircleOutlineIcon from '@mui/icons-material/RemoveCircleOutline';
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';

export default function CartItem() {
  return (
    <div className='px-5'>
          <div className='lg:flex items-center lg:space-x-5'>
              <div>
                  <img className="w-[5rem] h-[5rem] object-cover "
                      src="https://images.unsplash.com/photo-1568901346375-23c9450c58cd?q=80&w=1899&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                      alt="" />
              </div>
              <div className='flex items-center justify-self-auto lg:w-[70%]'>
                  <div className='space-y-1 lg:space-y-3 w-full'>
                      <p>biryani</p>
                      <div className='flex justify-between items-center'>
                          <div className='flex items-center space-x-1'>
                              <IconButton>
                                  <RemoveCircleOutlineIcon />
                              </IconButton>
                              <div className='w-4 h-5 text-xs flex items-center justify-center'>
                                  {5}
                              </div>
                              <IconButton>
                                  <AddCircleOutlineIcon />
                              </IconButton>
                          </div>
                          <p>2343</p>
                      </div>
                      <div className='pt-3 space-x-2'>
                          {
                              [1, 1, 1, 1,].map((item) => (
                                <Chip label="ing" />
                          
                          ))}
                          
                      </div>
                  </div> 
              </div>
        </div>
    </div>
  )
}
