package com.cyro.cravekart.service;

import com.cyro.cravekart.request.AssignDeliveryPartnerRequest;
import com.cyro.cravekart.request.USER_STATUS;
import com.cyro.cravekart.response.DeliveryPartnerAdminResponse;
import com.cyro.cravekart.response.DeliveryPartnerLocationResponse;

import java.util.List;

public interface DeliveryPartnerService {
  public List<DeliveryPartnerAdminResponse> listPartners(
      Boolean available, Boolean verified, USER_STATUS userStatus
  );
  public boolean activatePartner(Long partnerId);
  public boolean deactivatePartner(Long partnerId);
  public void assignPartnerToOrder(Long orderId, Long partnerId);
  public void unassignPartnerToOrder(Long orderId);
  public DeliveryPartnerLocationResponse trackLocation(Long partnerId);

}
