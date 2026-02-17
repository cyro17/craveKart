import React from "react";
import { useSelector } from "react-redux";

const img =
    "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=400&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8OHx8Zm9vZHxlbnwwfHwwfHx8MA%3D%3D";

export default function CartSummary() {
    const { items, cartTotal } = useSelector((state) => state.cart);
    // console.log(items);
    return (
        <div className="bg-white text-black rounded-3xl p-6 shadow-sm h-fit sticky top-12 mt-5">
            {/* cart header */}
            <div className="flex justify-between items-center">
                <h3 className="font-semibold">Cart (2)</h3>
                <div>i</div>
            </div>

            {/* items */}
            <div>
                {items.map((item, index) => (
                    <div
                        key={index}
                        className="flex items-center border-b text-sm py-4  mb-6 gap-4"
                    >
                        <img
                            src={item?.images[0]}
                            alt=""
                            className="h-20 w-20 rounded-xl object-cover"
                        />
                        <div className="flex flex-col gap-1 w-full">
                            <div className=" flex justify-between w-full">
                                <span className="font-semibold">
                                    {" "}
                                    {item?.foodName}
                                </span>
                                <span> {item?.quantity}</span>
                            </div>
                            <div className="text-gray-400">
                                {item.totalPrice}
                            </div>
                        </div>
                    </div>
                ))}
            </div>

            {/* promotion */}
            <div className="flex flex-col justify-between gap-x-3">
                <h4 className="font-medium mb-3">Promo code</h4>
                <div className="flex gap-2">
                    <input
                        className="flex-1 h-10 mb-2 items-center border rounded-lg px-4 text-sm focus:outline-none focus:ring-2 focus:ring-rose-400"
                        type="text"
                        placeholder="add promo code"
                    />
                    <button className="bg-gray-200 h-10 px-4 mb-3 rounded-lg text-sm hover:bg-gray-300">
                        apply
                    </button>
                </div>
            </div>
            {/* totals */}
            <div className="space-y-2 text-sm mb-6">
                <div className="flex justify-between">
                    <span>Item price</span>
                    <span>{cartTotal}</span>
                </div>
                <div className="flex justify-between">
                    <span>shipping fee</span>
                    <span>price</span>
                </div>
                <div className="flex justify-between">
                    <span>restaurant charges</span>
                    <span>price</span>
                </div>
                <div className="flex justify-between">
                    <span>platform fee</span>
                    <span>price</span>
                </div>
                <div className="flex justify-between font-semibold text-lg mb-6">
                    <span>total</span>
                    <span>Grand Total</span>
                </div>
            </div>
            <div>welcome to our food order app</div>
        </div>
    );
}
