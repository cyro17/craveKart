import axios from "axios";

export const API_URL = "http://localhost:9091/api";

const api = axios.create({
    baseURL: API_URL,
    headers: {
        "Content-Type": "application/json",
    }
})

api.interceptors.request.use(config => {
    // This function runs before the request is sent
    console.log('Request intercepted:', config);

    const token = localStorage.getItem("token");
    if (token) config.headers.Authorization = `Bearer ${token}`;
    return config;
}, error => {
    console.error('Request error:', error);
    return Promise.reject(error);
});

export default api;

