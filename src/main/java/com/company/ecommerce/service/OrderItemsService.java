package com.company.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.ecommerce.model.OrderItem;
import com.company.ecommerce.repository.OrderItemsRepository;

@Service
public class OrderItemsService {

	@Autowired
	OrderItemsRepository orderItemsRepository;

	public void addOrderedProducts(OrderItem orderItem) {
		orderItemsRepository.save(orderItem);
	}
}