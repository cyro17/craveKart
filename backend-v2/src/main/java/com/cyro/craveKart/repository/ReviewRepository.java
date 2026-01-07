package com.cyro.craveKart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cyro.craveKart.model.Review;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

}
