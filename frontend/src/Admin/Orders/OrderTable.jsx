import {
  Avatar, AvatarGroup, Box, Card, CardHeader, Table,
  TableBody, TableCell, TableContainer, TableHead, TableRow
} from '@mui/material'
import React from 'react'
import { useSelector } from 'react-redux'

export default function OrderTable({ name, isDashboard }) {
  const { restaurantsOrder } = useSelector(store => store);
  return (
    <Box>
      <Card>
        <CardHeader
          title={name}
          sx={{
            pt: 2, 
            alignItems: "center", 
            "& .MuiCardHeader-action": { mt: 0.6 },
          }}
        />
        <TableContainer>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Id</TableCell>
                <TableCell>Image</TableCell>
                <TableCell>Price</TableCell>
                <TableCell>Name</TableCell>

                {!isDashboard && <TableCell>Ingredients</TableCell>}
                {!isDashboard && <TableCell>Status</TableCell>}
                {
                  !isDashboard && (
                    <TableCell sx={{ textAlign: "center"}}>Update</TableCell>
                  )
                }
              </TableRow>
            </TableHead>
            <TableBody>
              {
                restaurantsOrder.orders
                  ?.slice(0, isDashboard ?
                    7 : restaurantsOrder.orders.length)
                  .map((item, index) => (
                    <TableRow
                      className='cursor-pointer'
                      hover
                      key={item.id}
                      sx={{
                        "&:last-of-type td, &:last-of-type th": { border: 0 },
                      }}
                    >
                      <TableCell>{item.id}</TableCell>
                      <TableCell>
                        <AvatarGroup max={4} sx={{ justifyContent: "start" }}>
                          {item.items.map((orderItem) => (
                            <Avatar
                              alt={orderItem.food.name}
                              src={orderItem.food?.images[0]}
                            />
                          ))}
                        </AvatarGroup>
                      </TableCell>

                      <TableCell>
                        {item?.customer.email}
                      </TableCell>

                      
                    </TableRow>
                  ))
              }
            </TableBody>
          </Table>
        </TableContainer>
      </Card>
    </Box>
  )
}
