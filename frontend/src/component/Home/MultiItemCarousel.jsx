import React from 'react';
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import { Slideshow } from '@mui/icons-material';
import Slider from 'react-slick';
import { topMeals } from './TopMeal';
import CarouselItem from './CarouselItem';

export default function MultiItemCarousel() {
    const settings = {
        dots: true, 
        inifinite: true, 
        speed: 500,  
        slidesToShow: 5,
        slidesToScroll: 1,
        autoplay: true,
        autoplaySpeed: 2000,
        arrows: false,
    };

    return (
        <div className='h-9'>
            <Slider {...settings}>
                {topMeals.map((meal, index) =>
                    <CarouselItem key={index} image={meal.image} title={meal.title} />)
                }
            </Slider>
        </div>
  );
}
