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
        <div>
            <Slider {...settings}>
                {topMeals.map((meal) =>
                    <CarouselItem image={meal.image} title={meal.title} />)
                }
            </Slider>
        </div>
  );
}
