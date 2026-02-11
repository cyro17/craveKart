import { Autoplay, Grid } from "swiper/modules";
import { Swiper, SwiperSlide } from "swiper/react";

import "swiper/css";
import "swiper/css/grid";

export default function Slider({ categories }) {
  return (
      <div className="category-section flex flex-col">
        
        <Swiper
            className="food-swiper"
            modules={[Grid, Autoplay]}
            slidesPerView={6}
            grid={{ rows: 2, fill: "row" }}
            spaceBetween={30}
            autoplay={{
            delay: 2000,
            disableOnInteraction: false,
            }}
            speed={800}
            breakpoints={{
            0: { slidesPerView: 3, grid: { rows: 2 } },
            640: { slidesPerView: 4, grid: { rows: 2 } },
            1024: { slidesPerView: 6, grid: { rows: 2 } },
            }}
        >
        {categories.map((cat) => (
          <SwiperSlide key={cat.id}>
            <div className="category-item ">
              <div className="circle-image">
                <img src={cat.image} alt={cat.title} />
              </div>
              <p className="text-2xl text-stone-900">{cat.title}</p>
            </div>
          </SwiperSlide>
        ))}
      </Swiper>
    </div>
  );
}