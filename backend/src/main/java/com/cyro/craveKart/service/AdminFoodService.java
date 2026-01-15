package com.cyro.cravekart.service;

import com.cyro.cravekart.models.Food;
import com.cyro.cravekart.request.AdminCreateFoodRequest;

public interface AdminFoodService {
  Food createFood(AdminCreateFoodRequest request);

}
