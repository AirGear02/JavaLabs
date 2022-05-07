package com.lab3.restaurant.dto.order;

import com.lab3.restaurant.model.OrderEntity;

public class UpdateOrderDto {
    private OrderEntity.OrderStatus status;
    private Double price;


    public UpdateOrderDto() {

    }

    public UpdateOrderDto(OrderEntity.OrderStatus status, Double price) {
        this.status = status;
        this.price = price;
    }

    public UpdateOrderDto(OrderEntity.OrderStatus status) {
        this.status = status;
    }

    public UpdateOrderDto(Double price) {
        this.price = price;
    }

    public OrderEntity.OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderEntity.OrderStatus status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
