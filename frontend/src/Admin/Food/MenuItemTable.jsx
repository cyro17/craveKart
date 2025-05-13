import { Box, Card, CardHeader, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from '@mui/material'
import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useLocation, useNavigate, useParams } from 'react-router-dom';


export default function MenuItemTable() {
  const location = useLocation();
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const { menu, ingredients, restaurant, auth } = useSelector(store => store);
  console.log("menu", menu);
  const { id } = useParams();
  const jwt = localStorage.getItem("jwt");
  
  return (
    <Box width={"100%"}>
      <Card>
        <CardHeader
          title="title"
          sx={{
            pt: 2, 
            alignItems: "center", 
          }}
        />
        <TableContainer>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Image</TableCell>
                <TableCell>Title</TableCell>
                <TableCell>Price</TableCell>
                <TableCell>Availability</TableCell>
                <TableCell>Delete</TableCell>
              </TableRow>
            </TableHead>
          </Table>
          <TableBody>
            {}
          </TableBody>
        </TableContainer>
      </Card>

    </Box>
  )
}
