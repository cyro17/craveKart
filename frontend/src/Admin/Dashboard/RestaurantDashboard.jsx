import React from 'react'
import AdminSidebar from '../AdminSidebar'
import { Grid2 } from '@mui/material'
import AvgCard from "../ReportCard/ReportCard";
import CurrencyRupeeIcon from "@mui/icons-material/CurrencyRupee";
import SellIcon from "@mui/icons-material/Sell";
import FastfoodIcon from "@mui/icons-material/Fastfood";

export default function RestaurantDashboard() {
  return (
    <>
    <div>
      <div className="px-2">
      
      <Grid2 container spacing={1}>
        <Grid2 item lg={3} xs={12}>
          <AvgCard
            title={"Total Earnings"}
            value={`Rs. ${450}`}
            growValue={70}
            icon={
              <CurrencyRupeeIcon sx={{ fontSize: "3rem", color: "gold" }} />
            }
            />
        </Grid2>
        <Grid2 item lg={3} xs={12}>
          <AvgCard
            title={"Total Sales"}
            value={`${390}`}
            growValue={35}
            icon={<SellIcon sx={{ fontSize: "3rem", color: "green" }} />}
            />
        </Grid2>
        <Grid2 item lg={3} xs={12}>
          <AvgCard
            title={"Sold Items"}
            value={`${299}`}
            growValue={30}
            icon={<FastfoodIcon sx={{ fontSize: "3rem", color: "blue" }} />}
            />
        </Grid2>
        <Grid2 item lg={3} xs={12}>
          <AvgCard
            title={"Left Items"}
            value={`${1}`}
            growValue={10}
            icon={<FastfoodIcon sx={{ fontSize: "3rem", color: "red" }} />
            
          }
          />
        </Grid2>
      </Grid2>
    </div>
      <AdminSidebar />
    </div>
  </>
  )
}
