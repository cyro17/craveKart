import React from 'react';
import { Divider, FormControl, FormControlLabel, Grid2, Radio, RadioGroup, Typography } from '@mui/material';
import PlaceIcon from '@mui/icons-material/Place';
import CalendarTodayIcon from '@mui/icons-material/CalendarToday';
import MenuCard from './MenuCard';

const categories = [
    "pizza", "biryani", "burger", "chinese", "south indian", "north indian"
];

const menu = [1, 1, 1, 1, 1, 1, 1, 1];

const foodTypes = [
    { lable: "All", value: "all" }, 
    { lable: "Vegetarian Only", value: "vegetarian" }, 
    { lable: "Non-Vegetarian", value: "non_vegetarian" },
    { lable: "Seasonal", value: "seasonal" }, 
]

export default function RestaurantDetails() {
    const [foodType, setFoodType] = React.useState("all");
    const [foodCategory, setFoodCategory] = React.useState("");
    const handleFilter = (event) => {
        console.log(event.target.value, event.target.name)
    }
  return (
      <div className='relative text-white-100 px-5 lg:px-20'>
          <section>
              <h3 className='text-gray-500 py-2 mt-10'>Home/india/indian fast food</h3> 
              <div>
                  <Grid2 container spacing={2}>
                      <Grid2 item size={{ xs:12} }>
                          <img
                              className='w-full h-[40vh] object-cover'
                              src="https://plus.unsplash.com/premium_photo-1661883237884-263e8de8869b?q=80&w=2689&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                           alt="" />
                      </Grid2>  
                      <Grid2 item size={{ xs:12, lg: 6} }>
                          <img
                              className='w-full h-[40vh] object-cover'
                              src="https://plus.unsplash.com/premium_photo-1679434184720-f729541052eb?q=80&w=2670&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                           alt="" />
                      </Grid2> 
                      <Grid2 item size={{ xs:12, lg: 6} }>
                          <img
                              className='w-full h-[40vh] object-cover'
                              src="https://images.pexels.com/photos/1267320/pexels-photo-1267320.jpeg?auto=compress&cs=tinysrgb&w=600"
                           alt="" />
                    </Grid2> 
                  </Grid2>
              </div>

              <div className='py-5'>
                  <h1 className='text-3xl font-semibold'>Indian Fast Food</h1>
                  <p className='text-gray-500 mt-1'>Lorem ipsum dolor sit amet consectetur adipisicing elit. Soluta libero exercitationem aut dicta cupiditate debitis quas veritatis dolor. Sed, nemo! Quis et voluptatem quisquam tenetur illum ipsa nisi quibusdam dolores.</p>
                  
                  <div className='space-y-3 mt-1'>
                    <p className='text-gray-500 flex items-center gap-2 py-2'>
                        <span><PlaceIcon/></span>
                        Ambavadi circle, Ahmedabad
                    </p>
                    <p className='text-gray-500 flex items-center gap-2'>
                        <span><CalendarTodayIcon/></span>
                        Mon-Sun: 9:00AM - 9:00PM (Today)
                    </p> 
                  </div>
                  
              </div>
          </section>
          <Divider />
          
          <section className='pt-[2rem] lg:flex relative'>
              <div className='space-y-10 lg: w-[20%] filter p-5 shadow-sm'>
                  <div className='box space-y-5 lg:sticky top-28'>
                      <div>
                          <Typography variant='h5' sx={{ paddingBottom: "1rem" }}>
                              Food Type
                          </Typography>

                          <FormControl className='py-10 space-y-5 component={"fieldset"}'>
                              <RadioGroup
                                  name='foodType'
                                  value={foodType}
                                  onChange={handleFilter}
                              >
                                  { foodTypes.map((item, index) =>
                                      <FormControlLabel
                                          key={index}
                                          value={item.value}
                                          control={<Radio />}
                                          label={item.lable}
                                      />
                                  )}
                              </RadioGroup>
                          </FormControl>
                      </div>
                      <Divider />
                      <div>
                          <Typography variant='h5' sx={{ paddingBottom: "1rem" }}>
                              Food Category
                          </Typography>

                          <FormControl className='py-10 space-y-5 component={"fieldset"}'>
                              <RadioGroup
                                  name='foodCategory-group'
                                  value={foodCategory}
                                  
                              >
                                  { categories.map((item, index) =>
                                      <FormControlLabel
                                          key={index}
                                          value={item}
                                          control={<Radio />}
                                          label={item}
                                      />
                                  )}
                              </RadioGroup>
                          </FormControl>
                      </div>
                  </div>
              </div>

              <div className='space-y-5 lg:w-[80%] lg:pl-10'>
                  {menu.map((item, index) => {
                      return <MenuCard key={index} label={ item} />
                  })}
              </div>
              
          </section>
    </div>
  )
}
