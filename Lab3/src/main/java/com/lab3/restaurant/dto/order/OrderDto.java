package com.lab3.restaurant.dto.order;

import com.lab3.restaurant.dto.dish.DishDto;
import com.lab3.restaurant.dto.user.UserInfoDto;
import com.lab3.restaurant.model.OrderEntity;

import java.sql.Timestamp;

public class OrderDto {
    private long id;
    private UserInfoDto userDto;
    private DishDto dishDto;

    private String createdAt;
    private Double price;
    private OrderEntity.OrderStatus status;

    public OrderDto() {

    }

    public OrderDto(long id, Timestamp createdAt, OrderEntity.OrderStatus status,
                    UserInfoDto userDto, DishDto dishDto, Double price) {
        this.id = id;
        this.createdAt = createdAt.toLocalDateTime().toString();
        this.userDto = userDto;
        this.dishDto = dishDto;
        this.price = price;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserInfoDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserInfoDto userDto) {
        this.userDto = userDto;
    }

    public DishDto getDishDto() {
        return dishDto;
    }

    public void setDishDto(DishDto dishDto) {
        this.dishDto = dishDto;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public OrderEntity.OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderEntity.OrderStatus status) {
        this.status = status;
    }
}
