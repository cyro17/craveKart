package com.cyro.craveKart.response;

import com.cyro.craveKart.model.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponse {
    private String message;
    private String jwt;
    private USER_ROLE role;
}
