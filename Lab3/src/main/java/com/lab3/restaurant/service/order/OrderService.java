package com.lab3.restaurant.service.order;

import com.lab3.restaurant.dto.order.OrderDto;
import com.lab3.restaurant.model.OrderEntity;

import java.util.List;
import java.util.Map;

public interface OrderService {
    void createOrder(long dishId, long userId);
    void updateOrderStatus(long orderId, OrderEntity.OrderStatus status);
    void setOrderPrice(long orderId, double price);
    List<OrderDto> getAllOrders();
    List<OrderDto> getUserOrders(long userId);

    Map<String, List<OrderDto>> getUserOrdersGroupedByStatus(long userId);
    Map<String, List<OrderDto>> getAllOrdersGroupedByStatus();
}
