import React, { useEffect } from 'react'
import RestaurantCard from '../../restaurant/RestaurantCard'
import { useDispatch, useSelector } from 'react-redux';
import { fetchRestaurants } from '../../../State/Restaurant/restaurantSlice';

export const restaurants = [
    {
      id: 1,
      name: "Spice Route",
      image: "https://images.unsplash.com/photo-1589308078059-be1415eab4c3",
      rating: 4.5,
      cuisine: "North Indian, Mughlai",
      isOpen: true,
      openStatus: "Open now",
      description: "Rich Mughlai curries and authentic North Indian flavors cooked to perfection.",
    },
    {
      id: 2,
      name: "Burger Bay",
      image: "https://images.unsplash.com/photo-1568901346375-23c9450c58cd",
      rating: 4.2,
      cuisine: "Burgers, Fast Food",
      isOpen: false,
      openStatus: "Closed",
      description: "Juicy burgers, crispy fries, and fast food classics for every craving.",
    },
    {
      id: 3,
      name: "Pizza Palazzo",
      image: "https://images.unsplash.com/photo-1548365328-8b849e6f0c7f",
      rating: null,
      cuisine: "Italian, Pizza",
      isOpen: true,
      openStatus: "Open now",
      description: "Stone-baked pizzas with fresh toppings and authentic Italian taste.",
    },
    {
      id: 4,
      name: "Wok & Roll",
      image: "https://images.unsplash.com/photo-1585032226651-759b368d7246",
      rating: 4.7,
      cuisine: "Chinese, Asian",
      isOpen: true,
      openStatus: "Open now",
      description: "Flavor-packed Chinese and Asian dishes tossed fresh in sizzling woks.",
    },
    {
      id: 5,
      name: "The Biryani House",
      image: "https://images.unsplash.com/photo-1633945274309-2c16c9682a8c",
      rating: 4.0,
      cuisine: "Biryani, Hyderabadi",
      isOpen: false,
      openStatus: "Closed",
      description: "Authentic Hyderabadi biryani slow-cooked with aromatic spices.",
    },
    {
      id: 6,
      name: "Taco Fiesta",
      image: "https://images.unsplash.com/photo-1551504734-5ee1c4a1479b",
      rating: 4.3,
      cuisine: "Mexican",
      isOpen: true,
      openStatus: "Open now",
      description: "Loaded tacos, nachos, and Mexican street-style favorites.",
    },
    {
      id: 7,
      name: "Sushi Zen",
      image: "https://images.unsplash.com/photo-1579871494447-9811cf80d66c",
      rating: 4.8,
      cuisine: "Japanese, Sushi",
      isOpen: true,
      openStatus: "Open now",
      description: "Freshly rolled sushi and premium Japanese delicacies.",
    },
    {
      id: 8,
      name: "Cafe Mocha",
      image: "https://images.unsplash.com/photo-1509042239860-f550ce710b93",
      rating: 4.1,
      cuisine: "Cafe, Desserts",
      isOpen: true,
      openStatus: "Open now",
      description: "Cozy cafe serving handcrafted coffee and sweet treats.",
    },
    {
      id: 9,
      name: "Grill Master",
      image: "https://images.unsplash.com/photo-1529193591184-b1d58069ecdd",
      rating: 4.4,
      cuisine: "BBQ, Grill",
      isOpen: false,
      openStatus: "Closed",
      description: "Smoky BBQ platters and grilled delights.",
    },
    {
      id: 10,
      name: "Green Bowl",
      image: "https://images.unsplash.com/photo-1512621776951-a57141f2eefd",
      rating: 4.6,
      cuisine: "Healthy, Salads",
      isOpen: true,
      openStatus: "Open now",
      description: "Fresh salads, smoothies, and nutritious bowls.",
    },
  ];
  


export default function RestaurantList() {
  const { list, loading, error } = useSelector(state => state.restaurant);
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(fetchRestaurants());
  }, [dispatch])
  
  if (loading) return <p>loading restaurants...</p>
  if (error) return <p className='text-red-500'>{ error}</p>
  return (
      <section className='p-2 w-[80vw] mx-auto mt-10'>
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
