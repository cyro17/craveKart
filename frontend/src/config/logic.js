


export const isPresentInFavorites = (fav, restaurant) => {
    for (let item of fav) {
        if (restaurant.id === item.id)
            return true;
        return false;
    }
}