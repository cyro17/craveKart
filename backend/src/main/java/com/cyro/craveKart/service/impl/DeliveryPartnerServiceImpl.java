package com.cyro.cravekart.service.impl;

import com.cyro.cravekart.models.Delivery;
import com.cyro.cravekart.models.DeliveryPartner;
import com.cyro.cravekart.models.Order;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.models.enums.OrderStatus;
import com.cyro.cravekart.repository.DeliveryPartnerRepository;
import com.cyro.cravekart.repository.DeliveryRepository;
import com.cyro.cravekart.repository.OrderRepository;
import com.cyro.cravekart.request.AssignDeliveryPartnerRequest;
import com.cyro.cravekart.request.USER_STATUS;
import com.cyro.cravekart.response.DeliveryPartnerAdminResponse;
import com.cyro.cravekart.response.DeliveryPartnerLocationResponse;
import com.cyro.cravekart.service.DeliveryPartnerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryPartnerServiceImpl implements DeliveryPartnerService {

//  ======================== ADMIN =============================================
  private final ModelMapper modelMapper;
  private final DeliveryPartnerRepository deliveryPartnerRepository;
  private final OrderRepository orderRepository;
  private final DeliveryRepository deliveryRepository;

  @Override
  public List<DeliveryPartnerAdminResponse> listPartners(Boolean available,
                                                         Boolean verified,
                                                         USER_STATUS userStatus) {

    return deliveryPartnerRepository.findWithFilters(available, verified, userStatus)
        .stream()
        .map(this::mapToAdminResponse)
        .toList();
  }

  @Override
  public boolean activatePartner(Long partnerId) {
    DeliveryPartner partner = getPartner(partnerId);
    partner.setVerified(true);
    partner.getUser().setStatus(USER_STATUS.ACTIVE);
    DeliveryPartner saved = deliveryPartnerRepository.save(partner);
    return true;
  }

  @Override
  public boolean deactivatePartner(Long partnerId) {
    DeliveryPartner partner = getPartner(partnerId);

    boolean hasActiveOrders = orderRepository.existsByDeliveryPartnerIdAndOrderStatusNot(
        partner.getId(), OrderStatus.DELIVERED
    );

    if(hasActiveOrders) {
      throw new IllegalStateException("Cannot deactivate partner with active deliveries");
    }

    partner.setAvailable(false);
    partner.setVerified(false);
    partner.getUser().setStatus(USER_STATUS.SUSPENDED);

    deliveryPartnerRepository.save(partner);
    return true;
  }

  @Override
  public void assignPartnerToOrder(Long orderId, Long partnerId) {


    DeliveryPartner partner = getPartner(partnerId);
    if(!partner.isVerified() || !partner.isAvailable()){
      throw new IllegalStateException("Cannot assign partner to order");
    }

    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new RuntimeException("Order not found"));

    if (order.getDeliveryPartnerId() != null) {
      throw new IllegalStateException(
          "Order already has a delivery partner"
      );
    }

    if (order.getOrderStatus() != OrderStatus.READY_FOR_PICKUP) {
      throw new IllegalStateException(
          "Order not ready for delivery"
      );
    }

    order.setDeliveryPartnerId(partnerId);
    order.setDeliveryPartnerName(partner.getUser().getFirstName());
    partner.setAvailable(false);
    orderRepository.save(order);
    deliveryPartnerRepository.save(partner);
  }

  @Override
  public void unassignPartnerToOrder(Long orderId) {

    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new RuntimeException("Order not found"));

    if (order.getDeliveryPartnerId() == null) {
      throw new IllegalStateException(
          "Order has no delivery partner"
      );
    }

    if (order.getOrderStatus() == OrderStatus.DELIVERED) {
      throw new IllegalStateException(
          "Cannot unassign delivered order"
      );
    }

    Long deliveryPartnerId = order.getDeliveryPartnerId();
    DeliveryPartner partner = deliveryPartnerRepository.findById(deliveryPartnerId).orElseThrow(
        () -> new RuntimeException("DeliveryPartner not found")
    );

    order.setDeliveryPartnerId(null);
    order.setDeliveryPartnerName(null);

    partner.setAvailable(true);
    orderRepository.save(order);
    deliveryPartnerRepository.save(partner);

  }

  @Override
  public DeliveryPartnerLocationResponse trackLocation(Long partnerId) {
    return null;
  }




//  =============================== UTIL ===============================

  private DeliveryPartner getPartner(Long partnerId) {
    return deliveryPartnerRepository.findById(partnerId)
        .orElseThrow(() ->
            new RuntimeException("Delivery partner not found"));
  }


  private DeliveryPartnerAdminResponse mapToAdminResponse(
      DeliveryPartner partner
  ) {
    DeliveryPartnerAdminResponse res =
        modelMapper.map(partner, DeliveryPartnerAdminResponse.class);

    User user = partner.getUser();
    res.setUsername(user.getUsername());
    res.setFirstName(user.getFirstName());
    res.setLastName(user.getLastName());
    res.setPhone(user.getContact() != null
        ? user.getContact().getMobile()
        : null);
    res.setUserStatus(user.getStatus());

    return res;
  }
}
