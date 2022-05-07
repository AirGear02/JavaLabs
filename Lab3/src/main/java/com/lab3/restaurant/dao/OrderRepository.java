package com.lab3.restaurant.dao;

import com.lab3.restaurant.model.OrderEntity;
import com.lab3.restaurant.model.UserEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByUser(UserEntity user);
    List<OrderEntity> findAllByUser(UserEntity user, Sort sort);
    Iterable<OrderEntity> findAllByStatus(OrderEntity.OrderStatus status);
}
