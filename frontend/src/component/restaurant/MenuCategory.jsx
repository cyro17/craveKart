import React, { useEffect } from "react";
import { motion } from "motion/react";
import { useDispatch, useSelector } from "react-redux";
import {
    addToCart,
    decrementCartItem,
    incrementCartItem,
} from "../../State/cart/cartThunk";

import { fetchRestaurantMenu } from "../../State/Restaurant/RestaurantThunks";
import { openCart } from "../../State/ui/uiSlice";
import toast from "react-hot-toast";

const img =
    "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8Zm9vZHxlbnwwfHwwfHx8MA%3D%3D";

export default function MenuCategory({ category, items }) {
    const dispatch = useDispatch();
    console.log(items);

    useEffect(() => {
        dispatch(fetchRestaurantMenu());
    }, [dispatch]);

    const { items: cartItems = [] } = useSelector((state) => state.cart);

    return (
        <div className="flex gap-12 text-black">
            {/* left - Category Title */}
            <div className="w-1/4 ">
                <div className="text-2xl font-thin sticky top-28">
                    <h2>{category ? category : "category"}</h2>
                </div>
            </div>
            <div className="w-12 h-1 bg-red-500 mt-2 rounded-full" />

            {/* Right side - food items */}
            <div className="w-3/4 space-y-6">
                {items?.map((item) => {
                    const cartItem = cartItems.find(
                        (i) => i.foodId === item.id
                    );
                    return (
                        <motion.div
                            key={item.id}
                            initial={{ opacity: 0, y: 15 }}
                            whileInView={{ opacity: 1, y: 0 }}
                            transition={{ duration: 0.3 }}
                            viewport={{ once: true }}
                            className="flex justify-between items-start border-b pb-6"
                        >
                            {/* Left - Food Info */}
                            <div className="max-w-xl">
                                <h3 className="text-lg font-semibold">
                                    {item.name}
                                </h3>
                                <p className="text-gray-500 text-sm mt-1">
                                    {item.description || "description"}
                                </p>
                                <p className="mt-3 font-semibold">
                                    {item.price}
                                </p>
                            </div>

                            {/* Image + Add button */}
                            <div className="relative">
                                <img
                                    // src={item?.images ? item.images[0] : img}
                                    src={img}
                                    alt={item.name}
                                    className="w-28 h-28 object-cover rounded-xl"
                                />
                                {!cartItem ? (
                                    <button
                                        onClick={async () => {
                                            try {
                                                await dispatch(
                                                    addToCart(item.id)
                                                ).unwrap();
                                                toast.success(
                                                    `${item.name} added to cart 🛒`
                                                );
                                                dispatch(openCart());
                                            } catch (err) {
                                                toast.error(
                                                    "Failed to add item"
                                                );
                                            }
                                        }}
                                        className="absolute -bottom-3 left-1/2 -translate-x-1/2 bg-white text-red-500 font-semibold px-6 py-1 rounded-md shadow-md border hover:bg-red-50 transition"
                                    >
                                        ADD
                                    </button>
                                ) : (
                                    <div className="absolute -bottom-3 left-1/2 -translate-x-1/2 bg-white text-red-500 font-semibold rounded-md flex items-center gap-4 px-4 py-1">
                                        <button
                                            onClick={() =>
                                                dispatch(
                                                    decrementCartItem(item.id)
                                                )
                                            }
                                        >
                                            -
                                        </button>
                                        <span>{cartItem?.quantity}</span>
                                        <button
                                            onClick={() =>
                                                dispatch(
                                                    incrementCartItem(
                                                        cartItem.cartItemId
                                                    )
                                                )
                                            }
                                        >
                                            +
                                        </button>
                                    </div>
                                )}
                            </div>
                        </motion.div>
                    );
                })}
            </div>
        </div>
    );
}
