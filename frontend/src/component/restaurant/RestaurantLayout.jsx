import React, { useEffect } from "react";
import { Outlet, useParams } from "react-router-dom";
import RestaurantHero from "./RestaurantHero";
import RestaurantTabs from "./RestaurantTabs";
import { useDispatch, useSelector } from "react-redux";
import { fetchRestaurantById } from "../../State/Restaurant/RestaurantThunks";
import { motion } from "motion/react";

function RestaurantLayout() {
    const { id } = useParams();
    console.log("id: ", id);
    const dispatch = useDispatch();

    useEffect(() => {
        console.log("dispatching with restaurant id :", id);
        dispatch(fetchRestaurantById({ id }));
    }, [id, dispatch]);

    const { selectedRestaurant, loading, error } = useSelector(
        (state) => state.restaurant
    );
    console.log("selected restaurant : ", selectedRestaurant);

    return (
        <motion.div
            className="flex flex-col min-h-screen lg:w-[75vw] md:w-[85vw] sm:w-full mx-auto bg-gray-50"
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            transition={{ duration: 0.5 }}
        >
            <div className="h-[50vh]">
                <RestaurantHero restaurant={selectedRestaurant} />
            </div>

            <div className="border-b sticky top-0 z-50">
                <div className="max-w-6xl px-4 sm:px-6 lg:px-8 mt-2">
                    <RestaurantTabs />
                </div>
            </div>

            <motion.div
                className="max-w-6xl mx-5 px-4 sm:px-6 lg:px-8 py-8"
                initial={{ y: 20, opacity: 0 }}
                animate={{ y: 0, opacity: 1 }}
                transition={{ duration: 0.5, delay: 0.2 }}
            >
                <Outlet />
            </motion.div>
        </motion.div>

        // <div className='bg-gray-50 shadow-sm min-h-screen flex flex-col'>

        //   <div className='bg-white h-[40vh] shadow-sm w-full '>
        //     <div className='max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-6'>
        //       <RestaurantHero restaurant={selectedRestaurant} />
        //     </div>
        //   </div>

        //   {/* Tabs */}
        //   <div className='bg-white border-b sticky top-100 z-40'>
        //     <div className='max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 overflow-x-auto'>
        //       <RestaurantTabs />
        //     </div>
        //   </div>

        //   <div className='max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-8 w-full'>
        //     <Outlet/>
        //   </div>
        //   </div>
    );
}

export default RestaurantLayout;
