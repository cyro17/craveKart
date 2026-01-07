import { isPresentInFavorites } from "../../config/logic";

export const reducers = {
    healthCheck: (state, action) => {
        state.isLoading = true;
        state.error = null;
        state.success = null;
        state.success = action.payload;

    },
    registerRequest: (state) => {
        state.isLoading = true;
        state.error = null;
        state.success = null;
    },
    loginRequest: (state) => {
        state.isLoading = true;
        state.error = null;
        state.success = null;
    },
    getUserRequest: (state) => {
        state.isLoading = true;
        state.error = null;
        state.success = null;
    },
    resetPasswordRequest: (state) => {
        state.isLoading = true;
        state.error = null;
        state.success = null;
    },
    requestResetPasswordRequest: (state) => {
        state.isLoading = true;
        state.error = null;
        state.success = null;
    },

    addToFavouriteRequest: (state) => {
        state.isLoading = true;
        state.error = null;
        state.success = null;
    },




    registerSuccess: (state, action) => {
        state.isLoading = false;
        state.jwt = action?.payload;
        state.success = "Register Success";
    },
    // add to fav success

    loginSuccess: (state, action) => {
        state.isLoading = false;
        console.log(action.payload.jwt)
        state.jwt = action.payload?.jwt;
        state.success = "Login Success";
    },

    getUserSuccess: (state, action) => {
        state.isLoading = false;
        state.user = action.payload;
        state.error = null;
    },
    requestResetPasswordSuccess: (state, action) => {
        state.isLoading = false;
        state.success = action.payload.message;
    },

    addToFavoriteSuccess: (state, action) => {
        state.isLoading = false;
        state.error = null;
        state.favorites = isPresentInFavorites(state.favorites, action.payload) ?
            state.favorites.filter((item) => item.id !== action.payload.id) :
            [action.payload, ...state.favorites];
    },

    registerFailure: (state, action) => {
        state.isLoading = false;
        state.error = action.payload;
    },
    loginFailure: (state, action) => {
        state.isLoading = false;
        state.error = action.payload;
    },
    getUserFailure: (state, action) => {
        state.isLoading = false;
        state.error = action.payload;
    },
    requestResetPasswordFailure: (state, action) => {
        state.isLoading = false;
        state.error = action.payload;
    },



    logout: (state) => {
        localStorage.removeItem("jwt");
        state.user = null;
        state.jwt = null;
        state.success = "logout success";
    }
};