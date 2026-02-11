import React, { useEffect } from 'react'
import RestaurantCard from '../../restaurant/RestaurantCard'
import { useDispatch, useSelector } from 'react-redux';
import { fetchRestaurants } from '../../../State/Restaurant/RestaurantThunks';
  

export default function RestaurantList() {
  const { list, loading, error } = useSelector(state => state.restaurant);
  const dispatch = useDispatch();


  useEffect(() => {
    dispatch(fetchRestaurants());
    console.log("token: ", localStorage.getItem("token"));
  }, [dispatch])

  console.log("restaurant list : ",list);
  
  if (loading) return <p>loading restaurants...</p>
  if (error) return <p className='text-red-500'>{ error}</p>
  return (
      <section className='p-2 w-[80vw] mx-auto my-10 py-10'>
          <h2 className="text-xl text-stone-900 font-semibold px-20">
            Best Picked Restaurants
        </h2>
          <div className='grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 px-20 rounded-full mt-3 group'>
              {
                  list.map(restaurant => (
                      <RestaurantCard key={restaurant.id} restaurant={restaurant} />
                  ))
              }
          </div>
    </section>
  )
}
