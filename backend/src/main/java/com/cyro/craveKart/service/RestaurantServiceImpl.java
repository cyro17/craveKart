package com.cyro.craveKart.service;

import com.cyro.craveKart.dto.RestaurantDTO;
import com.cyro.craveKart.exception.RestaurantException;
import com.cyro.craveKart.model.Address;
import com.cyro.craveKart.model.Restaurant;
import com.cyro.craveKart.model.User;
import com.cyro.craveKart.repository.AddressRepository;
import com.cyro.craveKart.repository.RestaurantRepository;
import com.cyro.craveKart.repository.UserRepository;
import com.cyro.craveKart.request.CreateRestaurantRequest;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Address address = addressRepository.save(req.getAddress());

        Restaurant restaurant = new Restaurant();

        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(req.getRegistrationDate());
        restaurant.setOwner(user);

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(ObjectId restaurantId, CreateRestaurantRequest updateReq) throws RestaurantException {
        Restaurant restaurant = findRestaurantById(restaurantId);
        if (updateReq.getCuisineType() != null) restaurant.setCuisineType(updateReq.getCuisineType());
        if (updateReq.getDescription() != null) restaurant.setDescription(updateReq.getDescription());

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(ObjectId restaurantId) throws RestaurantException {
        Restaurant restaurant = findRestaurantById(restaurantId);
        if (restaurant != null) {
            restaurantRepository.delete(restaurant);
        } else {
            throw new RestaurantException("Restaurant with id : " + restaurantId + " Not Found !! ");
        }
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(ObjectId id) throws RestaurantException {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if (restaurant.isPresent()) {
            return restaurant.get();
        } else {
            throw new RestaurantException("Restaurant Not Found !! ");
        }
    }

    @Override
    public Restaurant getRestaurantByUserId(ObjectId userId) throws RestaurantException {
        return restaurantRepository.findByOwnerId(userId);
    }

    @Override
    public RestaurantDTO addtoFavourites(ObjectId restaurantId, User user) throws RestaurantException {
        Restaurant res = findRestaurantById(restaurantId);
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setTitle(res.getName());
        restaurantDTO.setImages(res.getImages());
        restaurantDTO.setId(res.getId());
        restaurantDTO.setDescription(res.getDescription());

        boolean isFavoured = false;
        List<RestaurantDTO> favorites = user.getFavorites();
        for (RestaurantDTO fav : favorites) {
            if (fav.getId().equals(restaurantId)) {
                isFavoured = true;
                break;
            }
        }
        if (isFavoured)
            favorites.removeIf(fav -> fav.getId().equals(restaurantId));
        else
            favorites.add(restaurantDTO);

        userRepository.save(user);
        return restaurantDTO;
    }

    @Override
    public Restaurant updateRestaurantStatus(ObjectId restaurantId) throws RestaurantException {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant findRestaurantByUserId(ObjectId userId) {
        return restaurantRepository.findByOwnerId(userId);
    }
}
