import React, { useEffect } from 'react'
import RestaurantCard from '../../restaurant/RestaurantCard'
import { useDispatch, useSelector } from 'react-redux';
import { fetchRestaurants } from '../../../State/Restaurant/RestaurantThunks';
  

export default function RestaurantList({restaurants, city, loading, error}) {

  
  if (loading) {
    return (
      <section className="w-[90vw] mx-auto my-12 text-center">
        <p className="text-gray-500 text-lg">
          Loading restaurants...
        </p>
      </section>
    );
  }
  if (error) {
    return (
      <section className="w-[90vw] mx-auto my-12 text-center">
        <p className="text-red-500 text-lg">{error}</p>
      </section>
    );
  }

  if (!restaurants || restaurants.length === 0) {
    return (
      <section className="w-[90vw] mx-auto my-12 text-center">
      <p className="text-gray-400 text-lg">
        No restaurants found {city && `in ${city}`}
      </p>
      </section>
    )
  }
  return (
      <section className='p-2 w-[80vw] mx-auto my-10 py-10'>
          <h2 className="text-xl text-stone-900 font-semibold px-20">
            Best Picked Restaurants
        </h2>
          <div className='grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 px-20 rounded-full mt-3 group'>
              {
                  restaurants.map(restaurant => (
                      <RestaurantCard key={restaurant.id} restaurant={restaurant} city={city} />
                  ))
              }
          </div>
    </section>
  )
}
