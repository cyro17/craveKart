import { Box, Button, Divider, Drawer, Typography } from "@mui/material";
import React, { useEffect } from "react";
import CartItem from "./CartItem";
import { useDispatch, useSelector } from "react-redux";
import { fetchCart } from "../../State/cart/cartThunk";
import toast from "react-hot-toast";
import { useNavigate } from "react-router-dom";
import { closeCart } from "../../State/ui/uiSlice";

export default function CartAside({ open, onClose }) {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const {
        items = [],
        cartTotal = 0,
        deliveryCharge = 40,
    } = useSelector((state) => state.cart);
    const { isAuthenticated } = useSelector((state) => state.auth);

    const findTotal = cartTotal + deliveryCharge;

    useEffect(() => {
        if (open) {
            dispatch(fetchCart());
        }
    }, [dispatch, open]);

    const handleCheckout = () => {
        if (!items.length) return;
        if (!isAuthenticated) {
            toast.error("Please login to contitnue");
            navigate("/account/login");
            return;
        }
        dispatch(closeCart());
        navigate("/checkout");
    };
    return (
        <Drawer
            anchor="right"
            open={open}
            onClose={onClose}
            PaperProps={{
                sx: {
                    width: {
                        xs: "100vw", // mobile
                        sm: "60vw",
                        md: "30vw",
                        lg: "32vw", // desktop
                    },
                    padding: 2,
                    background: "rgba(255, 255, 255, 0.6)",
                    color: "#1a1a1a",
                    backdropFilter: "blur(20px)",
                    WebkitBackdropFilter: "blur(20px)",

                    borderLeft: "1px solid rgba(255,255,255,0.4)",
                    boxShadow: "0 8px 32px rgba(0,0,0,0.2)",
                },
            }}
            BackdropProps={{
                sx: {
                    backdropFilter: "blur(4px)",
                    backgroundColor: "rgba(0, 0, 0, 0.1)",
                },
            }}
        >
            <div className="">
                <Typography fontWeight={800}>Cart</Typography>

                <div className="mt-2 flex-1 overflow-y-auto">
                    {items.length === 0 ? (
                        <div className="flex flex-col mt-5 items-center justify-center h-64 text-gray-500">
                            <Typography variant="h6" fontWeight={600}>
                                Empty cart
                            </Typography>
                            <Typography variant="body2" className="mt-2">
                                Add Come delicious food to your cravings.
                            </Typography>
                        </div>
                    ) : (
                        <div className="mt-2 border-b-2">
                            {items?.map((item) => (
                                <CartItem key={item.CartItemId} item={item} />
                            ))}
                        </div>
                    )}

                    {/* Price Breakdown */}
                    {items.length > 0 && (
                        <div className="sticky bottom-0 ">
                            <div className="flex justify-between">
                                <Typography variant="body2">
                                    {" "}
                                    SubTotal
                                </Typography>
                                <Typography variant="body2">
                                    ₹{cartTotal}
                                </Typography>
                            </div>

                            <div className="flex justify-between">
                                <Typography>Delivery Fee</Typography>
                                <Typography>₹40.00</Typography>
                            </div>

                            <Divider className="my-1" />

                            <div className="flex justify-between mb-2">
                                <Typography className="font-bold">
                                    Total
                                </Typography>
                                <Typography className="font-extrabold">
                                    {findTotal}
                                </Typography>
                            </div>

                            <Button
                                onClick={handleCheckout}
                                variant="contained"
                                fullWidth
                                size="large"
                                disabled={!items.length}
                                className="text-2xl font-bold rounded-lg py-2 bg-gradient-to-r from-rose-500 to-pink-500
                        hover:scale-102 transition-all duration-200"
                            >
                                Proceed To Checkout
                            </Button>
                        </div>
                    )}
                </div>
            </div>
        </Drawer>
    );
}
