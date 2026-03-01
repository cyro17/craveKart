import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchAddress } from "../../../State/address/addressThunk";
import { setSelectedAddress } from "../../../State/address/addressSlice";
import { motion } from "motion/react";
import AddressForm from "../components/AddressForm";
import { placeOrder } from "../../../State/order/orderThunk";

export default function CheckoutShipping() {
    const [deliveryType, setDeliveryType] = useState("DELIVERY");
    const dispatch = useDispatch();

    const {
        addresses: list,
        selectedAddress,
        loading,
    } = useSelector((state) => state.address);

    const { loading: orderLoading } = useSelector((state) => state.order);

    // const list = [];
    // console.log(list);
    const [isModalOpen, setModalOpen] = useState(false);

    useEffect(() => {
        dispatch(fetchAddress());
    }, [dispatch]);

    function handleContinue() {
        if (!selectedAddress) return;
        dispatch(
            placeOrder({
                addressId: selectedAddress.id,
                deliveryType,
                paymentMethod: "CARD",
                voucherCode: "Welcome50",
                specialInstructions: "",
            })
        );
    }

    return (
        <div className="bg-white rounded-2xl shadow-sm overflow-hidden">
            {/* Header */}
            <div className="px- pt-6 pb-4 border-b border-gray-100 px-2">
                <div className="flex items-center justify-between gap-4">
                    <div>
                        <h2 className="text-xl font-bold text-gray-900">
                            Delivery Address
                        </h2>
                        <p className="text-sm text-gray-500 mt-0.5">
                            Select where we should deliver your food
                        </p>
                    </div>
                    {/* Toggle Pickup / Delivery */}
                    <div className="flex bg-gray-100 rounded-lg p-1 gap-1 ">
                        {["DELIVERY", "PICKUP"].map((type) => (
                            <button
                                key={type}
                                className={`px-4 py-2 rounded-lg text-sm font-medium transition-all duration-200
                            ${
                                deliveryType === type
                                    ? "bg-white  shadow-sm text-gray-700 font-semibold"
                                    : "text-gray-500 hover:text-gray-700"
                            }`}
                                onClick={() => setDeliveryType(type)}
                            >
                                {type === "DELIVERY" ? "Delivery" : "Pickup"}
                            </button>
                        ))}
                    </div>
                </div>
            </div>

            {/* Address List */}
            <div className="px-6 py-4 space-y-3 max-h-80 overflow-y-auto">
                {list.length === 0 && (
                    <div className="text-center py-8 text-gray-400">
                        <svg
                            className="w-12 h-12 mx-auto mb-3 text-gray-200"
                            fill="none"
                            viewBox="0 0 24 24"
                            stroke="currentColor"
                        >
                            <path
                                strokeLinecap="round"
                                strokeLinejoin="round"
                                strokeWidth={1.5}
                                d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"
                            />
                            <path
                                strokeLinecap="round"
                                strokeLinejoin="round"
                                strokeWidth={1.5}
                                d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"
                            />
                        </svg>
                        <p className="text-sm">no saved address</p>
                        <p className="text-xs text-gray-400">
                            Add one to get started
                        </p>
                    </div>
                )}
                {list?.map((address) => {
                    const isSelected = selectedAddress?.id === address.id;

                    return (
                        <motion.div
                            key={address.id}
                            onClick={() =>
                                dispatch(setSelectedAddress(address))
                            }
                            layout
                            whileHover={{ y: -1 }}
                            transition={{
                                type: "spring",
                                stiffness: 400,
                                damping: 40,
                            }}
                        >
                            <div className="absolute top-4">
                                {isSelected && (
                                    <motion.div
                                        initial={{ scale: 0 }}
                                        animate={{ scale: 1 }}
                                        className=""
                                    />
                                )}
                            </div>

                            {/* Selected Badge */}

                            {/* ADdress icon + info */}
                            <div className="flex item-start gap-3">
                                <div
                                    className={`w-9 h-9 rounded-lg flex items-center justify-center flex-shrink-0 mt-0.5
                                    ${
                                        isSelected
                                            ? "bg-rose-100"
                                            : "bg-gray-200"
                                    }`}
                                >
                                    <svg
                                        width="16"
                                        height="16"
                                        viewBox="0 0 24 24"
                                        fill="none"
                                    >
                                        <path
                                            d="M12 2C8.13 2 5 5.13 5 9C5 14.25 12 22 12 22C12 22 19 14.25 19 9C19 5.13 15.87 2 12 2ZM12 11.5C10.62 11.5 9.5 10.38 9.5 9C9.5 7.62 10.62 6.5 12 6.5C13.38 6.5 14.5 7.62 14.5 9C14.5 10.38 13.38 11.5 12 11.5Z"
                                            fill={
                                                isSelected
                                                    ? "#F43F5E"
                                                    : "#9CA3AF"
                                            }
                                        />
                                    </svg>
                                </div>
                                <div>
                                    <p className="font-semibold text-gray-900 text-sm">
                                        {address.firstName} {address.lastName}
                                    </p>
                                    <p className="text-gray-600 text-xs mt-0.5">
                                        {address.streetAddress}
                                        {address.landmark
                                            ? `, ${address.landmark}`
                                            : ""}
                                    </p>
                                    <p className="font-semibold text-xs text-gray-500 ">
                                        {" "}
                                        {address.city}, {address.state} -{" "}
                                        {address.zipCode}
                                    </p>
                                </div>
                            </div>
                        </motion.div>
                    );
                })}
            </div>
            {/* Footer  :Add address + continue */}

            <div className="px-6 py-4 pt-3 flex items-center justify-between gap-4 border-t border-gray-100">
                <button
                    className="flex items-center gap-2 font-medium text-rose-500 text-sm hover:text-rose-600 transition-colors
                    border border-rose-200 px-4 py-2 rounded-xl "
                    onClick={() => setModalOpen(true)}
                >
                    <svg
                        width="16 "
                        height="16"
                        viewBox="0 0 24 24"
                        fill="none"
                    >
                        <circle
                            cx="12"
                            cy="12"
                            r="10"
                            stroke="currentColor"
                            strokeWidth="1.5"
                        />
                        <path
                            d="M12 8V16M8 12H16"
                            stroke="currentColor"
                            strokeWidth="1.5"
                            strokeLinecap="round"
                        />
                    </svg>
                    Add New Address
                </button>

                <button
                    onClick={handleContinue}
                    disabled={!selectedAddress || loading}
                    className={`flex-1 max-w-xs py-2.5 rounded-xl font-semibold text-sm transition-all duration-200
                        ${
                            selectedAddress && !loading
                                ? "bg-gray-900 hover:bg-rose-600 text-white shadow-sm cursor-pointer"
                                : "bg-gray-100 text-gray-400 cursor-not-allowed"
                        }`}
                >
                    {orderLoading ? (
                        <span className="flex-nowrap items-center  justify-center gap-2">
                            <span className="w-4 h-4 rounded-full border-2 border-white/30 border-t-white animate-spin">
                                Placing Order...
                            </span>
                        </span>
                    ) : (
                        "Continue to Payment ->"
                    )}
                </button>
            </div>

            <AddressForm
                isOpen={isModalOpen}
                onClose={() => setModalOpen(false)}
                onSave={() => {
                    setModalOpen(false);
                }}
            />
        </div>
    );
}
