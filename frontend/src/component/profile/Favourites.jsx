import React from 'react'
import RestaurantCard from '../restaurant/RestaurantCard'

export default function Favourites() {
  return (
    <div className='py-5 text-xl font-semibold text-center'>
      <div className='flex flex-wrap gap-4 justify-center'>
        Favourite page
        {/* {[1, 1, 1].map((item, index) =>
          <RestaurantCard/>
        )} */}
      </div>
    </div>
  )
}
