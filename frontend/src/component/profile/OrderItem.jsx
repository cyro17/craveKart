import { Grid2, Typography } from "@mui/material";
import React from "react";

export default function OrderItem({ item }) {
    console.log(item);
    return (
        <Grid2
            container
            spacing={4}
            // justifyContent="space-between"
            alignItems="center"
            sx={{ mt: 1 }}
        >
            <Grid2>
                <div>
                    <img
                        src={item?.imageUrl ?? ""}
                        alt="food item image"
                        className="w-16 h-16 object-cover rounded-md shadow-md"
                    />
                </div>
            </Grid2>

            <Grid2 sx={{ flexGrow: 1 }}>
                <Typography fontWeight={600}>{item.foodName}</Typography>
                <Typography variant="body2">
                    Quantity: {item.quantity}
                </Typography>
            </Grid2>

            {/* Price */}
            <Grid2>
                <Typography fontWeight={400}>₹ {item.totalPrice}</Typography>
            </Grid2>
        </Grid2>
    );
}
