import { createSlice } from "@reduxjs/toolkit";
import { reducers } from "./reducers";

const initialState = {
    ingredients: [],
    update: null,
    category: []
}

const ingredientSlice = createSlice({
    name: "admin/ingredients",
    initialState,
    reducers: reducers
});

export const {
    getIngredients, getIngredientCategorySuccess,
    createIngredientCategorySuccess, createIngredientSuccess,
    updateStock
} = ingredientSlice.actions;

export const ingredientsReducer = ingredientSlice.reducer;