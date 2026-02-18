import { Button, Card, Chip, Grid2, Paper, Typography } from "@mui/material";
import React from "react";
import OrderItem from "./OrderItem";
import { ORDER_STATUS_CONFIG } from "../UI/orderStatusConfig";
import StatusChip from "../UI/StatusChip";

export default function OrderCard({ order, pricing, ship, status }) {
    let date = new Date(String(order.createdAt));
    date = date.toDateString();

    // console.log(pricing);

    return (
        <Paper
            className="p-4 mb-4 rounded-xl"
            elevation={4}
            sx={{ backgroundColor: "white" }}
        >
            {/* Header */}
            <Grid2
                container
                justifyContent="space-between"
                alignItems="center"
                sx={{
                    backgroundColor: "#f5f5f5",
                    padding: 1,
                    borderRadius: 2,
                }}
            >
                <div>
                    <Typography variant="body2" className="text-slate-500-">
                        Order placed
                    </Typography>
                    <Typography fontWeight={600}>{date}</Typography>
                </div>
                <div>
                    <Typography variant="body2">TOTAL</Typography>
                    <Typography fontWeight={600}>{pricing?.total}</Typography>
                </div>
                <div>
                    <Typography variant="body2">SHIP TO</Typography>
                    <Typography fontWeight={600}>{ship}</Typography>
                </div>
                <div>
                    <StatusChip status={status} />
                </div>
            </Grid2>

            <div>
                {order.items.map((item, index) => (
                    <OrderItem key={index} item={item} />
                ))}
            </div>
            <div className="flex gap-3 mt-4">
                <Button variant="secondary">Track Package</Button>
                <Button variant="seondary">View Details</Button>
            </div>
        </Paper>
    );
}
