package com.cyro.cravekart.response;

import com.cyro.cravekart.request.USER_STATUS;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeliveryPartnerAdminResponse {

  private Long id;

  // User info
  private String username;
  private String firstName;
  private String lastName;
  private String phone;

  private USER_STATUS userStatus;

  // Partner info
  private boolean available;
  private boolean verified;

  private LocalDateTime createdAt;
}
