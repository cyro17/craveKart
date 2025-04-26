
import axios from "axios";
import { api, API_URL } from "../../../component/config/api";

export function registerUser(reqData) {
    return async function (dispatch) {
        const { userData, navigate } = reqData;
        console.log("formdata : ", userData);
        dispatch({ type: "auth/registerRequest" });
        try {
            const { data } = await axios.post(`${API_URL}/auth/signup`, userData);
            // console.log("response data :", data);

            if (data.jwt) {
                localStorage.setItem("jwt", data.jwt);
                dispatch({ type: "auth/registerSuccess", payload: data.jwt });
                navigate("/");
            }
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

        } catch (error) {
            const errorMessage = error.message;
            dispatch({ type: "auth/getUserFailure", payload: errorMessage });
        }
    }
}

export function loginUser(reqData) {
    return async function (dispatch) {
        try {
            dispatch({ type: "auth/loginRequest" });
            const { data } = await axios.post(`${API_URL}/auth/signin`, reqData.data);
            console.log("data : ", data);

            if (data.jwt) localStorage.setItem("jwt", data.jwt);
            dispatch({ type: "auth/loginSuccess", payload: data });
            reqData.navigate("/");
        } catch (error) {
            dispatch({
                type: "auth/loginFailure", payload:
                    error.response && error.response.data.message ?
                        error.response.data.message : error.message
            });
        }
    }
}

export function logout() {
    return async function (dispatch) {
        dispatch({ type: "auth/logout" });
        localStorage.removeItem("jwt");
    }
}

