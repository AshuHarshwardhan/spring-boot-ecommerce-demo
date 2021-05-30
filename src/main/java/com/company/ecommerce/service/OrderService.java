package com.company.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.ecommerce.dto.cart.CartDto;
import com.company.ecommerce.dto.cart.CartItemDto;
import com.company.ecommerce.dto.order.PlaceOrderDto;
import com.company.ecommerce.model.Order;
import com.company.ecommerce.model.OrderItem;
import com.company.ecommerce.model.User;
import com.company.ecommerce.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	CartService cartService;

	@Autowired
	OrderItemsService orderItemsService;

	public Order saveOrder(PlaceOrderDto orderDto, User user) {
		Order order = new Order(orderDto, user);
		return orderRepository.save(order);
	}

	public List<Order> listOrders(User user) {
		return orderRepository.findAllByUserOrderByCreatedDateDesc(user);
	}

	public Optional<Order> getOrder(int orderId) {
		return orderRepository.findById(orderId);
	}

	@Transactional
	public void placeOrder(User user) {
		CartDto cartDto = cartService.listCartItems(user);

		PlaceOrderDto placeOrderDto = new PlaceOrderDto();
		placeOrderDto.setUser(user);
		placeOrderDto.setTotalPrice(cartDto.getTotalCost());

		Order newOrder = saveOrder(placeOrderDto, user);
		
		List<CartItemDto> cartItemDtoList = cartDto.getcartItems();
		for (CartItemDto cartItemDto : cartItemDtoList) {
			OrderItem orderItem = new OrderItem(newOrder, cartItemDto.getProduct(), cartItemDto.getQuantity(),
					cartItemDto.getProduct().getPrice());
			orderItemsService.addOrderedProducts(orderItem);
		}

		cartService.deleteUserCartItems(user);
	}
}
