package com.example.nurgalykovArman.repositories;

import com.example.nurgalykovArman.enums.OrderStatus;
import com.example.nurgalykovArman.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Метод для получения заказов с фильтрацией
    @Query("SELECT o FROM Order o WHERE (:status IS NULL OR o.status = :status) " +
            "AND (:minPrice IS NULL OR o.totalPrice >= :minPrice) " +
            "AND (:maxPrice IS NULL OR o.totalPrice <= :maxPrice)")
    List<Order> findOrdersByStatusAndTotalPriceBetween(@Param("status") OrderStatus status,
                                                       @Param("minPrice") Double minPrice,
                                                       @Param("maxPrice") Double maxPrice);

}