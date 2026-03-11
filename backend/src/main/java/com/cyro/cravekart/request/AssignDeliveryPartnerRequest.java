package com.cyro.cravekart.request;

import lombok.Data;

@Data
public class AssignDeliveryPartnerRequest {
  private Long orderId;
  private Long deliveryPartnerId;
}
