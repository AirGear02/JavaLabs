package com.lab3.restaurant.controller;

import com.lab3.restaurant.dto.order.CreateOrderDto;
import com.lab3.restaurant.dto.order.OrderDto;
import com.lab3.restaurant.dto.order.UpdateOrderDto;
import com.lab3.restaurant.dto.user.UserInfoDto;
import com.lab3.restaurant.model.OrderEntity;
import com.lab3.restaurant.service.order.OrderService;
import com.lab3.restaurant.service.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }


    @PostMapping("/orders")
    public String createOrder(CreateOrderDto orderDto, Principal principal) {
        UserInfoDto user = userService.getByEmail(principal.getName());
        orderService.createOrder(orderDto.getDishId(), user.getId());
        return "redirect:/my-orders";
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping("/orders/{orderId}")
    public RedirectView updateOrder(@PathVariable long orderId, UpdateOrderDto orderDto) {
        String newStatus = "";
        if (orderDto.getStatus() != null) {
            orderService.updateOrderStatus(orderId, orderDto.getStatus());
            newStatus = orderDto.getStatus().name();
        }

        if (orderDto.getPrice() != null) {
            orderService.setOrderPrice(orderId, orderDto.getPrice());
        }
        return new RedirectView("/orders?status=" + newStatus);

    }

    @GetMapping("/my-orders")
    public String getMyOrders(Model model,
                              @RequestParam("status") Optional<String> status,
                              Principal principal) {
        UserInfoDto user = userService.getByEmail(principal.getName());
        Map<String, List<OrderDto>> orders = orderService.getUserOrdersGroupedByStatus(user.getId());
        OrderEntity.OrderStatus orderStatus = status
                .map(OrderEntity.OrderStatus::valueOf)
                .orElse(OrderEntity.OrderStatus.WAITING_APPROVAL);

        model.addAttribute("status", orderStatus.name());
        model.addAttribute("orders", orders.get(orderStatus.name()));
        return "my-orders";
    }


    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping("/orders")
    public String getAllOrder(Model model, @RequestParam("status") Optional<String> status) {
        Map<String, List<OrderDto>> orders = orderService.getAllOrdersGroupedByStatus();
        OrderEntity.OrderStatus orderStatus = status
                .map(OrderEntity.OrderStatus::valueOf)
                .orElse(OrderEntity.OrderStatus.WAITING_APPROVAL);

        model.addAttribute("status", orderStatus.name());
        model.addAttribute("orders", orders.get(orderStatus.name()));
        return "my-orders";
    }


    @GetMapping("/orders/cook")
    public String getAllOrder(Model model) {
        Map<String, List<OrderDto>> orders = orderService.getAllOrdersGroupedByStatus();
        model.addAttribute("orders", orders.get(OrderEntity.OrderStatus.SENT_TO_KITCHEN.name()));
        return "cook-orders";
    }

    @PreAuthorize("hasRole('COOK')")
    @PostMapping("/orders/{orderId}/cook")
    public RedirectView cookOrder(@PathVariable long orderId) {
        orderService.updateOrderStatus(orderId, OrderEntity.OrderStatus.COOKED);
        return new RedirectView("/orders/cook");
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/orders/{orderId}/pay")
    public RedirectView payOrder(Model model, @PathVariable long orderId) {
        orderService.updateOrderStatus(orderId, OrderEntity.OrderStatus.PAID);
        return new RedirectView("/my-orders?status=" + OrderEntity.OrderStatus.PAID.name());
    }
}
