import axios from "axios";

export const API_URL = "http://localhost:9091/api";

export const api = axios.create({
    baseURL: API_URL,
    headers: {
        "Content-Type": "application/json",
    }
})

// api.post('/auth/health-check').then(response => {
//     console.log("API Health Check:", response.data);
// }).catch(error => {
//     console.error("API Health Check Failed:", error);
// });