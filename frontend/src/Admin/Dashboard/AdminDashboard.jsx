import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux'
import { useParams } from 'react-router-dom';
import RestaurantCard from './RestaurantCard';
import AddRestaurantCard from './AddRestaurantCard';
import { getRestaurantByUserId } from '../../State/Customers/Restaurant/actions';

export default function AdminDashboard() {
  const { restaurant } = useSelector(store => store);
  const params = useParams();
  const dispatch = useDispatch();
  console.log("restaurant", restaurant);
  
  useEffect(() => { 
    dispatch(getRestaurantByUserId());
  }, [])

  return (
    <div className='lg: px-20'>
      <div className='lg: flex flex-wrap justify-center'>
        {restaurant.usersRestaurant?.map((item) => (
          <RestaurantCard item={ item} />
        ))}
        {restaurant.usersRestaurant.length < 1 &&
          <AddRestaurantCard />}
      </div>
    </div>
  )
}
