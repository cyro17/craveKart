import { Card, FormControl, FormControlLabel, Radio, RadioGroup, Typography } from '@mui/material'
import React from 'react'
import OrderTable from './OrderTable';


const orderStatus = [
  { label: "Pending", value: "PENDING" },
  { label: "Completed", value: "COMPLETED" },
  // { label: "Out For Delivery", value: "OUT_FOR_DELIVERY" },
  // { label: "Delivered", value: "DELIVERED" },
  { label: "All", value: "all" },
];


export default function RestaurantOrder() {
  
  return (
    <div>
      <Card>
        <Typography sx={{
          paddingBottom: "1rem"
        }}
        variant='h5'
        >
          Order Status
        </Typography>
        <FormControl>
          <RadioGroup>
            {orderStatus.map((item, index) => (
              <FormControlLabel
                key={index}
                value={item.value}
                control={<Radio />}
                label={item.label}
                sx={{
                  color: "gray"
                }}  
              />
            ))}
          </RadioGroup>
        </FormControl>
      </Card>
      <OrderTable name={"All Orders"}/>
    </div>
  )
}
