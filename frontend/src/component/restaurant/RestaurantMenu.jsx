import React, { useEffect } from "react";
import { motion } from "motion/react";
import MenuCategory from "./MenuCategory";
import { useParams } from "react-router-dom";
import { restaurantMenus } from "../../seeds/restaurantMenu";
import { useDispatch, useSelector } from "react-redux";
import { fetchRestaurantMenu } from "../../State/Restaurant/RestaurantThunks";

function RestaurantMenu() {
    const { id } = useParams();
    const dispatch = useDispatch();

    const { menu } = useSelector((state) => state.restaurant);

    useEffect(() => {
        dispatch(fetchRestaurantMenu({ id }));
    }, [dispatch]);

    return (
        <motion.div
            // initial={{ opacity: 0, y: 20 }}
            // animate={{ opacity: 1, y: 0 }}
            // transition={{ duration: 0.4 }}
            className="space-y-12"
        >
            {menu?.categories?.map((cat) => (
                <motion.div
                    key={cat.id}
                    // initial={{ opacity: 0, y: 30 }}
                    // animate={{ opacity: 1, y: 0 }}
                >
                    <MenuCategory
                        key={cat.id}
                        category={cat.name}
                        items={cat.foods}
                    />
                </motion.div>
            ))}
        </motion.div>
    );
}

export default RestaurantMenu;
