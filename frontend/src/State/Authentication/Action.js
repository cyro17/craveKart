
import { api, API_URL } from "../../component/config/api";
import axios from "axios";


export function registerUser(reqData) {
    return async function (dispatch) {
        const { userData, navigate } = reqData;
        console.log("formdata : ", userData);
        dispatch({ type: "auth/registerRequest" });
        try {
            const { data } = await axios.post(`${API_URL}/auth/signup`, userData);
            console.log("response data :", data);

            if (data.jwt) localStorage.setItem("jwt", data.jwt);

            else navigate("/login");
        } catch (err) {
            console.log(err);
            dispatch({ type: "auth/registerFail", payload: err });
        }
    }
}

export function healthCheck() {
    return async function (dispatch) {
        dispatch({ type: "auth/registerRequest" })
        try {
            const { data } = await axios.get(`${API_URL}/auth/healthCheck`);
            console.log("get data : ", data)
            dispatch({ type: "auth/healthCheck", payload: data });

        } catch (error) {
            console.log(error)
        }
    }
}

export function getUser(token) {
    return async function (dispatch) {
        dispatch({ type: "auth/getUserRequest" });
        try {
            const response = await api.get("/api/users/profile", {
                headers: {
                    Authorization: `Bearer ${token}`,
                }
            });
            const user = response.data;
            console.log("user", user);
            dispatch({ type: "auth/getUserSuccess", payload: user })
            console.log("req user ", user);
        } catch (error) {
            const errorMessage = error.message;
            dispatch({ type: "auth/getUserFailure", payload: errorMessage });
        }
    }
}