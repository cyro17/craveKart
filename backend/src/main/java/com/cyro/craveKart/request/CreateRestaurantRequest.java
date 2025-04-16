package com.cyro.craveKart.request;

import com.cyro.craveKart.model.Address;
import com.cyro.craveKart.model.ContactInformation;
import lombok.Data;

import java.util.List;

@Data
public class CreateRestaurantRequest {
    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHours;
    private String closingHours;
    private List<String> images;


}
