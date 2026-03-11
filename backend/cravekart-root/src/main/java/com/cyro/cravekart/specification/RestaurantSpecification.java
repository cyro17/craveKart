package com.cyro.cravekart.specification;

import com.cyro.cravekart.models.Restaurant;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class RestaurantSpecification {

  public static Specification<Restaurant> hasCity(String city) {
    return (root, query, cb) -> {
      return cb.equal(
          cb.lower(
              root.join("address", JoinType.INNER).get("city")), city.toLowerCase()
      );
    };
  }

  public static Specification<Restaurant> hasCuisine(String cuisine) {
    return (root, query, cb)->
        cb.equal(
            cb.lower(root.get("cuisineType")),
            cuisine.toLowerCase()
        );
  }

  public static Specification<Restaurant> hasMinRating(Double rating) {
    return  (root, query, cb)->
        rating ==null ? null : cb.greaterThanOrEqualTo(root.get("rating"), rating);
  }
}
