import React, { useEffect } from 'react';
import './Home.css';
import Auth from '../Auth/Auth';
import Hero from './Hero/Hero';

import FoodCategories from './FoodCategories/FoodCategories';
import RestaurantList from './RestaurantList/RestaurantList';
import { useParams, useSearchParams } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { fetchRestaurants } from '../../State/Restaurant/RestaurantThunks';





export default function Home() {
  const { city } = useParams();
  const [searchParams] = useSearchParams();
  const dispatch = useDispatch();

  const { restaurants, loading, error} = useSelector(state => state.restaurant);

  const cuisine = searchParams.get("cuisine");
  const rating = searchParams.get("rating");
  const sort = searchParams.get("sort");

  useEffect(() => {
    if (city) {
      dispatch(fetchRestaurants())
    }
    
  }, [city, cuisine, rating, sort, dispatch]);

    return (
      
        <main className=' '>
          <Hero city={ city} />
          <FoodCategories />
          <RestaurantList
            restaurants={restaurants}
            loading={loading}
            error={error}
            city={city}
          /> 
      </main>  
      
  )
}
