package com.lab3.restaurant.dao;

import com.lab3.restaurant.model.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<DishEntity, Long> {

}
