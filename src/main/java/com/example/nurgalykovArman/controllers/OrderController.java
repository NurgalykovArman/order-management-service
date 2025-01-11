package com.example.nurgalykovArman.controllers;

import com.example.nurgalykovArman.enums.OrderStatus;
import com.example.nurgalykovArman.models.Order;
import com.example.nurgalykovArman.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Создание заказа
    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.ok("Order was successfully created");
    }

    // Обновление заказа
    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long orderId, @RequestBody Order orderDetails) {
        System.out.println(orderId + " " + orderDetails.getCustomerName());
        Optional<Order> updatedOrder = orderService.updateOrder(orderId, orderDetails);

        return updatedOrder.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Получение заказов с фильтрацией
    @GetMapping
    public ResponseEntity<List<Order>> getOrders(
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        List<Order> orders = orderService.getOrders(status, minPrice, maxPrice);
        return ResponseEntity.ok(orders);
    }

    // Мягкое удаление заказа
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> softDeleteOrder(@PathVariable Long orderId) {
        boolean isDeleted = orderService.softDeleteOrder(orderId);
        if (isDeleted) {
            return ResponseEntity.ok("Order has been successfully soft-deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");
        }
    }
}