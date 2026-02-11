import React from 'react'
import { topMeals } from '../TopMeal';
import Slider from './Slider';
import "./FoodCategories.css";

export default function FoodCategories() {

  return (
    // <div>Category</div>
    <section className="p-2 w-[80vw] mx-auto mt-10">        
        <h2 className="text-xl text-stone-900 font-semibold px-20">
          Order from our Favourites
        </h2>
      
      <Slider categories={topMeals} />
   </section>
  )
}
