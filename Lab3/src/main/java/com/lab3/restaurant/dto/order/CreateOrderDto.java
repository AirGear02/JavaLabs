package com.lab3.restaurant.dto.order;

public class CreateOrderDto {
    private long dishId;

    public CreateOrderDto(long dishId) {
        this.dishId = dishId;
    }

    public long getDishId() {
        return dishId;
    }

    public void setDishId(long dishId) {
        this.dishId = dishId;
    }
}
