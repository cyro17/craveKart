package com.cyro.cravekart.controllers;

import com.cyro.cravekart.response.RestaurantPartnerResponse;
import com.cyro.cravekart.service.AdminService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurantPartners")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Slf4j
public class AdminRestaurantPartnerController {

  private final AdminService adminService;

  @GetMapping("check")
  public ResponseEntity<String> check() {
    return ResponseEntity.ok().body("OK");
  }

  @GetMapping("/pending")
  public ResponseEntity<List<RestaurantPartnerResponse>> getPending() {
    return ResponseEntity.ok(adminService.getPendingRestaurantPartners());
  }

  @GetMapping("/{partnerId}")
  public ResponseEntity<RestaurantPartnerResponse> getApplication(@PathVariable Long partnerId) {
    return ResponseEntity.ok(adminService.getPendingRestaurantPartner(partnerId));
  }

  @PatchMapping("/{partnerId}/approve")
  public ResponseEntity<String> approve(@PathVariable Long partnerId) {

    adminService.approvePartner(partnerId);
    return ResponseEntity.ok("Partner approved");
  }

  @PatchMapping("/{partnerId}/reject")
  public ResponseEntity<String> reject(@PathVariable Long partnerId, @RequestParam String reason) {

    adminService.rejectPartner(partnerId, reason);
    return ResponseEntity.ok("Partner Rejected");
  }

  // PATCH suspend active partner
  @PatchMapping("/{partnerId}/suspend")
  public ResponseEntity<String> suspend(@PathVariable Long partnerId, @RequestParam String reason) {
    adminService.suspendPartner(partnerId, reason);
    return ResponseEntity.ok("Partner suspended");
  }

  // PATCH reactivate suspended partner
  @PatchMapping("/{partnerId}/reactivate")
  public ResponseEntity<String> reactivate(@PathVariable Long partnerId) {
    adminService.reactivatePartner(partnerId);
    return ResponseEntity.ok("Partner reactivated");
  }
}
