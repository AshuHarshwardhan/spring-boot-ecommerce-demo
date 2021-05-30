package com.company.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.ecommerce.model.OrderItem;

public interface OrderItemsRepository extends JpaRepository<OrderItem, Integer> {
}
