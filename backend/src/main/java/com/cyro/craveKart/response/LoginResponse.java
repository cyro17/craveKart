package com.cyro.cravekart.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
  String jwt;
  boolean status;
  String message;
  UserResponse user;

}
