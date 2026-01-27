package com.cyro.cravekart.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Embeddable
public class RestaurantDto {
    private  Long id;
    @NotBlank(message = "Name of the restaurant should be provided")
    private String name;

//    @JsonIgnore
//    private RestaurantPartnerDto restaurantPartner;

    @Column(length = 1000)
    private List<String> images;

    private String description;


}
