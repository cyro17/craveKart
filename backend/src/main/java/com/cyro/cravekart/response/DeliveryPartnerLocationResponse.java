package com.cyro.cravekart.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DeliveryPartnerLocationResponse {

  private Long partnerId;
  private Double latitude;
  private Double longitude;
  private LocalDateTime lastUpdated;
}

