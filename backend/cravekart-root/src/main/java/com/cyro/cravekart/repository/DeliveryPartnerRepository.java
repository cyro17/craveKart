package com.cyro.cravekart.repository;

import com.cyro.cravekart.models.DeliveryPartner;
import com.cyro.cravekart.request.USER_STATUS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartner, Long> {

  @Query("""
        SELECT dp FROM DeliveryPartner dp
        JOIN dp.user u
        WHERE (:available IS NULL OR dp.available = :available)
          AND (:verified IS NULL OR dp.verified = :verified)
          AND (:status IS NULL OR u.status = :status)
    """)
  List<DeliveryPartner> findWithFilters(
      Boolean available, Boolean verified, USER_STATUS userStatus
  );
}
