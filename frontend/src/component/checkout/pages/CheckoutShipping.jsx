import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchAddress } from "../../../State/address/addressThunk";
import { setSelectedAddress } from "../../../State/address/addressSlice";
import { motion } from "motion/react";
import AddressForm from "../components/AddressForm";

export default function CheckoutShipping() {
    const dispatch = useDispatch();
    const { addresses: list, selectedAddress } = useSelector(
        (state) => state.address
    );
    console.log(list);
    const [isModalOpen, setModalOpen] = useState(false);

    useEffect(() => {
        dispatch(fetchAddress());
    }, [dispatch]);

    function onSave(values) {}
    return (
        <div className="bg-white text-gray-800 rounded-2xl border-b  border-r-gray-600 p-6 shadow-sm">
            <div className="flex justify-between items-center mb-6">
                <h2 className="text-lg font-semibold ">Address</h2>

                <div className="flex bg-gray-100 rounded-full">
                    <button className="px-5 py-2 text-sm bg-white rounded-full shadow font-medium">
                        Delivery
                    </button>
                    <button className="px-5 py-2 text-sm text-gray-500">
                        Pickup
                    </button>
                </div>
            </div>
            <div>
                {list?.map((address, index) => {
                    const isSelected = selectedAddress?.id === address.id;
                    return (
                        <motion.div
                            key={address.id}
                            onClick={() =>
                                dispatch(setSelectedAddress(address))
                            }
                            layout
                            initial={false}
                            animate={{
                                scale: isSelected ? 1.01 : 1,
                            }}
                            whileHover={{
                                scale: 1.01,
                                y: -2,
                            }}
                            transition={{
                                type: "spring",
                                stiffness: 300,
                                damping: 40,
                                mass: 1,
                                ease: "easeInOut",
                            }}
                            className={`p-4 rounded-2xl mx-2 cursor-pointer border-b border-gray-200${
                                isSelected
                                    ? "bg-rose-50 border-rose-400 ring-2 ring-rose-300"
                                    : ""
                            }`}
                        >
                            {isSelected && (
                                <motion.p
                                    initial={{ opacity: 0, y: -5 }}
                                    animate={{ opacity: 1, y: 0 }}
                                    transition={{ duration: 0.2 }}
                                    className="text-xs bg-rose-500 w-fit rounded-lg  text-white p-1 font-semibold mb-2"
                                >
                                    selected
                                </motion.p>
                            )}
                            <p className="font-semibold">
                                {address?.firstName} {address?.lastName}
                            </p>
                            <p>
                                {address?.streetAddress}, {address.landmark}
                            </p>
                            <p>
                                {address?.city} {address?.state}{" "}
                                {address?.zipCode}
                            </p>
                        </motion.div>
                    );
                })}
            </div>
            <button
                className="mt-10 px-6 bg-gray-400 rounded-lg h-10 mx-4 font-semibold text-black hover:scale-102 transition"
                onClick={() => setModalOpen(true)}
            >
                + Add New Address
            </button>
            <AddressForm
                isOpen={isModalOpen}
                onClose={() => setModalOpen(false)}
                onSave={(data) => setSelectedAddress(data)}
            />
        </div>
    );
}
