import React from 'react';
import './Home.css';
import Auth from '../Auth/Auth';
import Hero from './Hero/Hero';

import FoodCategories from './FoodCategories/FoodCategories';
import RestaurantList from './RestaurantList/RestaurantList';





export default function Home() {

    return (
      <>
        <main className=' '>
          <Hero />
          <FoodCategories/>

          <RestaurantList/> 
        </main>  

        <Auth/>
      </>
    
  )
}
