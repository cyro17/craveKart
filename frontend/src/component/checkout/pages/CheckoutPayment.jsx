import React from "react";

export default function CheckoutPayment() {
    return (
        <div className="text-black bg-white rounded-2xl p-6 shadow-sm">
            <h3 className="font-semibold mb-6">Payment</h3>
            <div className="flex justify-between items-center">
                <div>
                    <p className="font-medium">Payment Method</p>
                    <p className="text-sm text-gray-500">
                        You can use your debit card to pay
                    </p>
                </div>
                <button className="border border-rose-400 px-3 bg-rose-300 rounded-full text-sm hover:bg-rose-100 transition">
                    + Add card
                </button>
            </div>
        </div>
    );
}
