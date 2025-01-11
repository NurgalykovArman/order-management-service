package com.example.nurgalykovArman.services;

import com.example.nurgalykovArman.enums.OrderStatus;
import com.example.nurgalykovArman.models.Order;
import com.example.nurgalykovArman.models.Product;
import com.example.nurgalykovArman.repositories.OrderRepository;
import com.example.nurgalykovArman.repositories.ProductRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    // Создание нового заказа
    public Order createOrder(Order order) {
        // Устанавливаем связь между заказом и продуктами
        List<Product> products = order.getProducts();
        if (products != null) {
            for (Product product : products) {
                product.setOrder(order);
            }
        }
        double totalPrice = 0.0;
        for (Product product : order.getProducts()) {
            totalPrice += product.getPrice() * product.getQuantity();
        }
        order.setTotalPrice(totalPrice);
        return orderRepository.save(order);
    }

    // Обновление существующего заказа
    public Optional<Order> updateOrder(Long orderId, Order orderDetails) {
        Optional<Order> existingOrder = orderRepository.findById(orderId);
        if (existingOrder.isPresent()) {
            Order updatedOrder = existingOrder.get();
            updatedOrder.setCustomerName(orderDetails.getCustomerName());
            updatedOrder.setStatus(orderDetails.getStatus());

            // Расчет общей стоимости
            double totalPrice = 0.0;
            for (Product product : orderDetails.getProducts()) {
                totalPrice += product.getPrice() * product.getQuantity();
                product.setOrder(updatedOrder); // Устанавливаем связь между продуктами и заказом
            }
            updatedOrder.setTotalPrice(totalPrice);

            // Обновляем список продуктов
            updatedOrder.getProducts().clear(); // Удаляем старые продукты
            updatedOrder.getProducts().addAll(orderDetails.getProducts()); // Добавляем новые

            return Optional.of(orderRepository.save(updatedOrder));
        }
        return Optional.empty();
    }


    // Получение заказов с фильтром
    public List<Order> getOrders(OrderStatus status, Double minPrice, Double maxPrice) {
        return orderRepository.findOrdersByStatusAndTotalPriceBetween(status, minPrice, maxPrice);
    }

    // Мягкое удаление заказа
    public boolean softDeleteOrder(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            Order existingOrder = order.get();
            existingOrder.setStatus(OrderStatus.CANCELLED); // Можно задать статус "отменен"
            orderRepository.save(existingOrder);
            return true;
        }
        return false;
    }
}
