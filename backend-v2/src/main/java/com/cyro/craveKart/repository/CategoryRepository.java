package com.cyro.craveKart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cyro.craveKart.model.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	public List<Category> findByRestaurantId(Long id);
}
