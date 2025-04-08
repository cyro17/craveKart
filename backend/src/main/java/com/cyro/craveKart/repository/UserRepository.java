package com.cyro.craveKart.repository;

import com.cyro.craveKart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u where u.status='PENDING'")
    List<User> getPendingRestaurantOwners();

    public User findByEmail(String username);
}
