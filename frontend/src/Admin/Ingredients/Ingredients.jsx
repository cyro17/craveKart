import { Button, Card, CardHeader, Grid2, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from '@mui/material'
import React from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { updateStock } from '../../State/Admin/Ingredients/ingredientsSlice';

const ingredients = {

}

export default function Ingredients(id) {
  const dispatch = useDispatch();
  const { ingredients, auth, restaurant } = useSelector(store => store);
  const jwt = localStorage.getItem("jwt");
  
  const handleUpdateStock = () => {
    dispatch(updateStock({ id, jwt }));
  }

  return (
    <div>
      <Grid2>
        <Grid2>
          <Card>
            <CardHeader
              title={"Ingredients"}
              sx={{

              }}
            />
            <TableContainer>
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell>Id</TableCell>
                    <TableCell>Name</TableCell>
                    <TableCell>Category</TableCell>
                    <TableCell>Availibility</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                {ingredients.ingredients.map((item, index) => (
                    <TableRow
                      className="cursor-pointer"
                      hover
                      key={item.id}
                      sx={{
                        "&:last-of-type td, &:last-of-type th": { border: 0 },
                      }}
                    >
                      <TableCell>{item?.id}</TableCell>
                      <TableCell className="">{item.name}</TableCell>
                      <TableCell className="">{item.category.name}</TableCell>

                      <TableCell className="">
                        <Button
                          onClick={() => handleUpdateStock(item.id)}
                          color={item.inStock ? "success" : "primary"}
                        >
                          {item.inStock ? "in stock" : "out of stock"}
                        </Button>
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          </Card>
        </Grid2>
      </Grid2>

    </div>
  )
}
