package com.example.nurgalykovArman.repositories;

import com.example.nurgalykovArman.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}