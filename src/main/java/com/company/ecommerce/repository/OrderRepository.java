package com.company.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.ecommerce.model.Order;
import com.company.ecommerce.model.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	List<Order> findAllByUserOrderByCreatedDateDesc(User user);
}
