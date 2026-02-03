import axios from "axios";
import { api, API_URL } from "../../component/config/api";


export function healthCheck() {
    return async function (dispatch) {
        dispatch({ type: "auth/check" })
        try {
            const { data } = await api.get(`${API_URL}/auth/check`);
            console.log("get data : ", data)
            dispatch({ type: "auth/healthCheck", payload: data });

        } catch (error) {
            console.log(error)
        }
    }
}

export function registerUser(reqData) {
    return async function (dispatch) {
        const { userData, navigate } = reqData;
        console.log("formdata : ", userData);
        dispatch({ type: "auth/registerRequest" });
        try {
            const { data } = await api.post(`${API_URL}/auth/signup`, userData);
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

export function loginUser(reqData) {
    return async function (dispatch) {
        try {
            dispatch({ type: "auth/loginRequest" });
            const { data } = await api.post(`${API_URL}/auth/signin`, reqData.values);
            console.log("login data : ", data);

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


// export function getUser(token) {
//     return async function (dispatch) {
//         dispatch({ type: "auth/getUserRequest" });
//         try {
//             const response = await api.get("/api/users/profile", {
//                 headers: {
//                     Authorization: `Bearer ${token}`,
//                 }
//             });
//             console.log(response.data)
//             dispatch({ type: "auth/getUserSuccess", payload: response.data })

//         } catch (error) {
//             const errorMessage = error.message;
//             dispatch({ type: "auth/getUserFailure", payload: errorMessage });
//         }
//     }
// }

// export function addToFavorites(reqData) {
//     return async function (dispatch) {
//         dispatch({ type: "auth/addToFavouriteRequest" });
//         try {
//             const response = await api.put(`api/restaurant/${reqData.restaurantId}/add-favorites`, {}, {
//                 headers: {
//                     Authorization: `Bearer ${reqData.jwt}`
//                 }
//             });
//             console.log("Add to favorite", response.data);
//             dispatch({ type: "auth/addToFavoriteSuccess", payload: response.data });
//         } catch (error) {
//             console.log("catch error ", error);
//             dispatch({
//                 type: "auth/addToFavoriteFailure",
//                 payload: error.message
//             });
//         }
//     }
// }


// export function resetPasswordRequest(reqData) {
//     return async function (dispatch) {
//         dispatch({ type: "auth/requestResetPasswordRequest" });
//         try {
//             const { data } = await api.post(
//                 `${API_URL}/auth/reset-password-request?email=${reqData.email}`, {}
//             );
//             console.log(data);
//             dispatch({ type: "auth/requestResetPasswordSuccess", payload: data });
//         } catch (error) {
//             console.log("error ", error);
//             dispatch({ type: "auth/requestResetPasswordFailure" });
//         }
//     }
// }





