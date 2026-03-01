import { createAsyncThunk } from "@reduxjs/toolkit";
import api, { API_URL } from "../../config/api";
import { fetchEventSource } from "@microsoft/fetch-event-source";

export const waitForPaymentReady = (token) => {
    return new Promise((resolve, reject) => {
        const controller = new AbortController();

        const timeout = setTimeout(() => {
            controller.abort();
            reject(new Error("Payment initialization timed out"));
        }, 30_000); // 30 seconds timeout


        fetchEventSource(`${API_URL}/notification/stream`, {
            method: "GET",
            headers: {
                Authorization: `Bearer ${token}`
            },
            signal: controller.signal,
            onopen: async (res) => {
                console.log("SSE opened status: ", res.status);
                if (res.status !== 200) {
                    clearTimeout(timeout);
                    controller.abort();
                    reject(new Error(`SSe failed to open : ${res.status}`));
                }
            },
            onmessage(event) {
                console.log("SSE event received: ", event.event, event.data);
                if (event.event === "payment-ready") {
                    try {
                        const data = JSON.parse(event.data);
                        clearTimeout(timeout);
                        controller.abort();
                        resolve(data.clientSecret);
                    } catch (err) {
                        clearTimeout(timeout);
                        controller.abort();
                        reject(new Error("Invalid payment-ready event data: " + err.message));
                    }
                }
            },
            onerror(err) {
                clearTimeout(timeout);
                controller.abort();
                reject(new Error("Error in payment initialization stream" + err.message));
            }
        }
        );
    })
}



export const placeOrder = createAsyncThunk(
    "order/placeOrder",
    async (values, { rejectWithValue }) => {
        try {

            const token = localStorage.getItem("token");

            const clientSecretPromise = waitForPaymentReady(token);

            const { data } = await api.post("/customer/order/place", values);
            console.log("order placed: ", data);

            const clientSecret = await clientSecretPromise;

            console.log("fetched clietn secret: ", clientSecret);

            return { ...data, clientSecret };
        } catch (err) {
            return rejectWithValue(err.response?.data || err.message);
        }
    }
)


export const fetchOrders = createAsyncThunk(
    "order/fetch",
    async (_, { rejectWithValue }) => {
        try {
            const { data } = await api.get("/customer/order");
            console.log(data);
            return data;
        } catch (err) {
            return rejectWithValue(err.response?.data || err.message);
        }
    }
)