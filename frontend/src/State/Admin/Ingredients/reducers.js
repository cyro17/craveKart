export const reducers = {
    getIngredients(state, action) {
        state.ingredients = action.payload;
    },

    getIngredientCategorySuccess(state, action) {
        state.category = action.payload;
    },

    createIngredientCategorySuccess(state, action) {
        state.category = [...state.category, action.payload];
    },

    createIngredientSuccess(state, action) {
        state.ingredients = [...state.ingredients, action.payload];
    },

    updateStock(state, action) {
        state.update = action.payload;
        state.ingredients = state.ingredients.map((item) =>
            item.id === action.payload.id ?
                action.payload : item
        );
    }
}