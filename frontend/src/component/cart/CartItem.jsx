import React, { useState } from "react";
import AddIcon from "@mui/icons-material/Add";
import RemoveIcon from "@mui/icons-material/Remove";
import DeleteIcon from "@mui/icons-material/Delete";
import { useDispatch, useSelector } from "react-redux";
import {
    decrementCartItem,
    incrementCartItem,
    removeCartItem,
} from "../../State/cart/cartThunk";

const img_fb =
    "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=500&auto=format&fit=crop&q=60";

export default function CartItem({ item }) {
    const dispatch = useDispatch();
    const { loadingItemId } = useSelector((state) => state.cart);

    const cartItemId = item.cartItemId;
    const isLoading = loadingItemId === cartItemId;

    return (
        <div className="flex gap-4 py-4 border-b border-gray-200">
            {/* Image */}
            <img
                src={item?.images?.length ? item.images[0] : img_fb}
                alt={item.foodName}
                className="w-20 h-20 rounded-lg object-cover"
            />

            {/* Details */}
            <div className="flex-1 flex flex-col justify-between">
                <h3 className="text-sm font-semibold text-gray-800">
                    {item.foodName}
                </h3>

                <div className="text-sm text-gray-400">{item?.description}</div>

                <div className="flex justify-between items-center mt-2">
                    {/* Quantity Controls */}
                    <div className="flex items-center gap-2 bg-gray-100 rounded-full px-2 py-1">
                        <button
                            disabled={isLoading}
                            className="p-1 hover:bg-gray-200 rounded-full transition disabled:opacity-50"
                            onClick={() =>
                                dispatch(decrementCartItem(cartItemId))
                            }
                        >
                            <RemoveIcon fontSize="small" />
                        </button>

                        <span className="text-sm font-medium w-5 text-center">
                            {item.quantity}
                        </span>

                        <button
                            disabled={isLoading}
                            className="p-1 hover:bg-gray-200 rounded-full transition disabled:opacity-50"
                            onClick={() =>
                                dispatch(incrementCartItem(cartItemId))
                            }
                        >
                            <AddIcon fontSize="small" />
                        </button>
                    </div>

                    {/* Price + Delete */}
                    <div className="flex items-center gap-3">
                        <span className="text-sm font-semibold text-gray-900">
                            ₹{Number(item.totalPrice).toFixed(2)}
                        </span>

                        <button
                            disabled={isLoading}
                            className="text-red-500 hover:text-red-600 transition disabled:opacity-50"
                            onClick={() => dispatch(removeCartItem(cartItemId))}
                        >
                            <DeleteIcon fontSize="small" />
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}
