package com.cyro.cravekart.controllers;

import com.cyro.cravekart.request.AssignDeliveryPartnerRequest;
import com.cyro.cravekart.request.USER_STATUS;
import com.cyro.cravekart.response.DeliveryPartnerAdminResponse;
import com.cyro.cravekart.service.DeliveryPartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/delivery-partners")
@RequiredArgsConstructor
public class AdminDeliveryPartnerController {

  private final DeliveryPartnerService deliveryPartnerService;

  @GetMapping
  public List<DeliveryPartnerAdminResponse> listPartners(
      @RequestParam(required = false) Boolean available,
      @RequestParam(required = false) Boolean verified,
      @RequestParam(required = false) USER_STATUS userStatus
  ) {
    return deliveryPartnerService.listPartners
        (available, verified, userStatus);
  }

  @PatchMapping("/{partnerId}/activate")
  public ResponseEntity<String> activatePartner(@PathVariable("partnerId") Long partnerId) {
    deliveryPartnerService.activatePartner(partnerId);
    return  new ResponseEntity<>("Delivery Partner activated", HttpStatus.OK);
  }

  @PatchMapping("/{partnerId}/deactivate")
  public ResponseEntity<String> deactivateDeliveryPartner(
      @PathVariable Long partnerId
  ) {
    deliveryPartnerService.deactivatePartner(partnerId);
    return new ResponseEntity("Delivery partner deactiavted", HttpStatus.ACCEPTED );
  }

  @PostMapping("/assign")
  public void assignDeliveryPartner(
      @RequestBody AssignDeliveryPartnerRequest request
  ) {
    deliveryPartnerService.assignPartnerToOrder(
        request.getOrderId(),
        request.getDeliveryPartnerId()
    );
  }

  @PostMapping("/unassign/{orderId}")
  public void unassignDeliveryPartner(
      @PathVariable Long orderId
  ) {
    deliveryPartnerService.unassignPartnerToOrder(orderId);
  }







}
