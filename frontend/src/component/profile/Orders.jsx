import React, { useEffect } from "react";
// import OrderCard from "./OrderCard";
import { useDispatch, useSelector } from "react-redux";
import { fetchOrders } from "../../State/order/orderThunk";

export default function Orders() {
    const dispatch = useDispatch();
    const data = useSelector((state) => state.order);
    useEffect(() => {
        dispatch(fetchOrders());
    }, [dispatch]);
    console.log(data);
    return (
        <div>Orders Page</div>
        // <div className='flex items-center flex-col h-[100vh]'>
        //   <h1 className='text-xl text-center py-7 font-semibold'>My Orders</h1>
        //   <div className='flex flex-col justify-between items-center space-x-7 '>
        //     {
        //       [1, 1, 1, 1].map((item, index) => (
        //         <OrderCard key={index}/>
        //       ))
        //     }
        //   </div>

        // </div>
    );
}
