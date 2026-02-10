import React from 'react'
import { topMeals } from '../TopMeal';


export default function FoodCategories() {

  return (
    <section className="mx-2 px-2 w-70vw  bg-base py-14">
        <h2 className="text-xl text-stone-900 font-semibold mb-6 px-20">
        Order our best food options
        </h2>

        <div className="lg:grid grid-cols-8 sm:flex flex-wrap gap-6 overflow-x-auto px-20">
        {topMeals.map(cat => (
            <div key={cat.title} className="text-center min-w-[90px]">
            <div className="h-24 w-24 rounded-full overflow-hidden  bg-white shadow-md">
                <img
                src={cat.image}
                alt={cat.title}
                className="h-full w-full object-cover"
                />
            </div>
            <p className="mt-2 text-sm text-stone-900">{cat.title}</p>
            </div>
        ))}
        </div>
  </section>
  )
}
