package com.lab3.restaurant.service.order;

import com.lab3.restaurant.dao.DishRepository;
import com.lab3.restaurant.dao.OrderRepository;
import com.lab3.restaurant.dao.UserRepository;
import com.lab3.restaurant.dto.dish.DishDto;
import com.lab3.restaurant.dto.order.OrderDto;
import com.lab3.restaurant.dto.user.UserInfoDto;
import com.lab3.restaurant.model.DishEntity;
import com.lab3.restaurant.model.OrderEntity;
import com.lab3.restaurant.model.UserEntity;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final DishRepository dishRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(DishRepository dishRepository, OrderRepository orderRepository,
                            UserRepository userRepository) {

        this.dishRepository = dishRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createOrder(long dishId, long userId) {
        UserEntity user = userRepository.getById(userId);
        DishEntity dish = dishRepository.getById(dishId);

        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setDish(dish);
        order.setStatus(OrderEntity.OrderStatus.WAITING_APPROVAL);
        order.setCreatedAt(Timestamp.from(Instant.now()));
        orderRepository.save(order);
    }

    @Override
    public void updateOrderStatus(long orderId, OrderEntity.OrderStatus status) {
        OrderEntity order = orderRepository.getById(orderId);
        order.setStatus(status);
        orderRepository.save(order);
    }

    @Override
    public void setOrderPrice(long orderId, double price) {
        OrderEntity order = orderRepository.getById(orderId);
        order.setPrice(price);
        orderRepository.save(order);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository
                .findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(order -> mapOrderToDto(order, true))
                .toList();
    }

    @Override
    public List<OrderDto> getUserOrders(long userId) {
        UserEntity user = userRepository.getById(userId);
        return orderRepository
                .findAllByUser(user, Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(order -> mapOrderToDto(order, false))
                .toList();
    }


    @Override
    public Map<String, List<OrderDto>> getUserOrdersGroupedByStatus(long userId) {
        return getUserOrders(userId).stream().collect(Collectors.groupingBy(order -> order.getStatus().name()));
    }

    @Override
    public Map<String, List<OrderDto>> getAllOrdersGroupedByStatus() {
        return getAllOrders().stream().collect(Collectors.groupingBy(order -> order.getStatus().name()));
    }

    private OrderDto mapOrderToDto(OrderEntity order, boolean withUser) {
        UserInfoDto userDto = null;

        if (withUser) {
            UserEntity user = order.getUser();
            userDto = new UserInfoDto(user.getFirstName(), user.getLastName(), user.getEmail(), user.getId());
        }

        DishEntity dish  = order.getDish();
        DishDto dishDto = new DishDto(dish.getId(), dish.getTitle(), dish.getDescription(), dish.getPhotoPath());
        return new OrderDto(order.getId(), order.getCreatedAt(), order.getStatus(), userDto, dishDto, order.getPrice());

    }
}
