import React, { useEffect, useRef } from 'react';
import './Home.css';
import MultiItemCarousel from './MultiItemCarousel';
import Auth from '../Auth/Auth';
import { useDispatch, useSelector } from 'react-redux';
import { getAllRestaurant } from '../../State/Customers/Restaurant/actions';
import RestaurantCard from '../restaurant/RestaurantCard';
import { Button } from '@mui/material';
import { motion, useScroll, useTransform } from "motion/react";

const MotionButton = motion(Button);

export default function Home() {

  const { auth, restaurant } = useSelector((store) => store);
  const dispatch = useDispatch();

  useEffect(() => {
    if (auth.user) {
      dispatch(getAllRestaurant(localStorage.getItem("jwt")));
    }
  }, [auth.user]);


    return (
      <div className=''>    
        
        {/* banner section */}
          <section className='banner -z-50 relative flex flex-col justify-center items-center'>
            <div className='w-[50vw] z-10 text-center'>
            <div className='text-4xl text-shadow-lg lg:font-extrabold  
              tracking-widest lg:text-8xl z-10 py-5'>
                CraveKart
              </div>
              <p className='z-10 text-gray-300 text-shadow-lg text-sm lg:text-3xl'>
                Karting Cravings to Your Door.
            </p> 
            <div className='flex mt-5 items-center justify-center gap-5 my-3  '>
              <MotionButton
                whileHover={{
                  scale: 1.4,
                  transition: { type: "spring", stiffness: 300, damping: 20 }
                }}
                variant='contained'
                className='h-14 w-[12vw]  text-gray-50'>
                <p className='font-bold min-w-full text-xl text-shadow-lg z-10 capitalize'>
                  Explore Meal
                </p>
              </MotionButton>
              <MotionButton
                whileHover={{
                scale: 1.4
                }}
                whileTap={{
                  scale: 0.9
                }}
                variant='contained'
                className='h-14 w-[12vw] text-gray-50'>
                <p className='font-bold min-w-full text-xl text-shadow-lg z-10 capitalize '>
                  Dine In
                </p>
                </MotionButton>
            </div>
            
            </div>  
          <div
            className='cover absolute top-0 left-0 right-0' />  
          <div
            className='absolute outline-none inset-0 backdrop-blur-md bg-gradient-to-b from-black/60 via-transparent to-black/80' />
        </section>

        {/* multicarousel section */}
        <section className='p-10 lg:py-10 lg:px-20'>
          <p className='text-2xl font-semibold text-grey-400 py-3 pb-10'>Top Meals</p>
          <MultiItemCarousel />
        </section>

        <section className='px-5 lg:px-20'>
          <h1 className='text-2xl font-semibold text-gray-100 py-3 pb-10'>
            Discover best restaurant 
          </h1>
          <div className='flex flex-wrap items-center justify-around'>
            {
              restaurant && restaurant.restaurants.map((item, index) =>
                <RestaurantCard data={item} index = {index} />)
            }
          </div>
        </section>
        <section>
          <footer className="bg-gradient-to-t from-black/90 to-black/90 p-4 text-white">
        
          <div
            className='absolute outline-none  backdrop-blur-lg bg-gradient-to-t from-black/90 via-transparent to-black/80' />
            <div className='footer'>
              <div className='flex justify-around w-[100vw] gap-10'>
                <div>logo</div>
                <div>Company</div>
                <div>Contact Us</div>
                <div>Available In</div>
                <div>
                  <div>Life at CraveKart</div>
                  <div>Social Links</div>
                </div>
              </div>
            </div>


          </footer>
        </section>

        
        <Auth/>
      </div>
  )
}
