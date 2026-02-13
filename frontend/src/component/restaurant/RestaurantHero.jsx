import StarIcon from "@mui/icons-material/Star";
import AccessTimeIcon from "@mui/icons-material/AccessTime";
import LocationOnIcon from "@mui/icons-material/LocationOn";
import { disableInstantTransitions, motion } from "motion/react";

import React from "react";
import RestaurantInfo from "./RestaurantInfo";

import { Swiper, SwiperSlide } from "swiper/react";
import { Autoplay, Pagination } from "swiper/modules";
const img =
    "https://images.unsplash.com/photo-1476224203421-9ac39bcb3327?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTh8fGZvb2R8ZW58MHwwfDB8fHww";

export default function RestaurantHero({ restaurant }) {
    // console.log("   restaurant : ", restaurant);
    return (
        <motion.div
            className="w-full h-[50vh]"
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            transition={{ duration: 0.8 }}
        >
            <div className="relative w-full h-full">
                <Swiper
                    modules={[Autoplay, Pagination]}
                    autoplay={{ delay: 3000 }}
                    loop
                    pagination={{ clickable: true }}
                    className="w-full h-full"
                >
                    {restaurant?.images.map((img, index) => (
                        <SwiperSlide>
                            <img
                                src={img}
                                alt={restaurant?.name || "restaurant"}
                                className="w-full h-full object-cover"
                            />
                            {/* overlay  */}
                            <div className="absolute top-0 left-0 w-full h-full bg-gradient-to-t from-black/90 via-black/40 to-black/10 opacity-90" />
                            <div className="absolute top-0 left-0 w-full h-full bg-gradient-to-t  from-black/90 via-black/40 to-black/10 opacity-90" />
                        </SwiperSlide>
                    ))}
                </Swiper>
                {/* <img
                    src={restaurant ? restaurant?.images?.[0] : img}
                    alt={restaurant?.name || "Restaurant"}
                    className="left-0 w-full  h-full object-cover"
                /> */}

                {/* overlay  */}
                <div className="absolute top-0 left-0 w-full h-full bg-gradient-to-t from-black via-transparent to-transparent opacity-90" />
                <div className="absolute top-0 left-0 w-full h-full bg-gradient-to-t from-black via-transparent to-transparent opacity-90" />

                {/* info section */}
                <div className="absolute bottom-0 left-0 w-full z-10 text-white px-4 py-2 sm:px-6 lg:px-8">
                    {/* Name  */}
                    <h1 className="text-4xl sm:text-4xl md:text-4xl font-extrabold tracking-tight shadow-md drop-shadow-[0_2px_6px_rgba(0,0,0,0.8)]">
                        {restaurant?.name || "restaurant name"}
                    </h1>

                    {/* Rating + time + open  */}
                    <div className="flex flex-wrap items-center gap-3 mt-2 text-sm sm:text-base font-semibold">
                        <div className="flex items-center px-2 py-1 bg-green-700 rounded-md">
                            ⭐ {restaurant?.ratingCount || 0} (1.2k+)
                        </div>

                        <div className="w-px h-5 bg-white opacity-50" />

                        <div>
                            <AccessTimeIcon style={{ fontSize: 18 }} />
                            <span className="px-1">25–30 mins</span>
                        </div>

                        <div className="w-px h-5 bg-white opacity-50 sm:block" />
                        <div>
                            <span>₹250 for two</span>
                        </div>

                        <div className="w-px h-5 bg-white opacity-50 sm:block" />

                        {restaurant?.open ? (
                            <span className="text-green-400 font-semibold">
                                Open Now
                            </span>
                        ) : (
                            <span className="font-semibold">
                                <span className="text-red-400">Closed</span> •
                                Opens at 10 AM
                            </span>
                        )}
                    </div>
                    <p className="mt-1 text-sm sm:text-base">
                        {restaurant ? restaurant?.cuisineType : "Cuisine Type"}
                    </p>
                    <div className="flex items-center gap-1 mt-1 text-sm sm:text-base">
                        <LocationOnIcon style={{ fontSize: 18 }} />
                        {restaurant ? restaurant?.fullAddress : "Address"}
                    </div>
                </div>
            </div>
        </motion.div>
    );
}
