package com.cyro.craveKart.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class ContactInfo {

    private String email;
    private String mobile;
    private String twitter;
    private String instagram;

}
