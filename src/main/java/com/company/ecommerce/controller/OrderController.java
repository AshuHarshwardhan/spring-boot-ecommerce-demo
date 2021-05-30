package com.company.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.ecommerce.exception.runtime.NotFoundException;
import com.company.ecommerce.model.Order;
import com.company.ecommerce.model.User;
import com.company.ecommerce.service.OrderService;
import com.company.ecommerce.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "OrderController", description = "Operations pertaining to orders in e-commerce application")
public class OrderController {

	@Autowired
	OrderService orderService;

	@Autowired
	UserService userService;

	/**
	 * Method used to place order for given userId.
	 * 
	 * @param userId as Integer
	 * @return ResponseEntity<HttpStatus>
	 * @throws NotFoundException
	 */
	@ApiOperation(value = "Place order for given userId")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully created"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@PostMapping("/orders")
	public ResponseEntity<HttpStatus> placeOrder(@RequestParam("userId") Integer userId) throws NotFoundException {
		Optional<User> userOptional = userService.findById(userId);
		if (!userOptional.isPresent()) {
			throw new NotFoundException("User with id " + userId + " not found.");
		}
		orderService.placeOrder(userOptional.get());
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	/**
	 * Method used to get all the orders for given userId
	 * 
	 * @param userId as Integer
	 * @return ResponseEntity<List<Order>>
	 */
	@ApiOperation(value = "Retrieve list of available orders for given userId", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 204, message = "Successfully retrieved list but list is empty"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping("/orders")
	public ResponseEntity<List<Order>> getAllOrders(@RequestParam("userId") Integer userId) {
		Optional<User> userOptional = userService.findById(userId);
		if (!userOptional.isPresent()) {
			throw new NotFoundException("User with id " + userId + " not found.");
		}
		List<Order> orderDtoList = orderService.listOrders(userOptional.get());
		return new ResponseEntity<List<Order>>(orderDtoList, HttpStatus.OK);
	}

	/**
	 * Method used to get order by id for given userId
	 * 
	 * @param id     as Integer
	 * @param userId as Integer
	 * @return ResponseEntity<List<Order>>
	 */
	@ApiOperation(value = "Retrieve details of order for given id and userId")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved order"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping("/orders/{id}")
	public ResponseEntity<Order> getOrder(@PathVariable("id") Integer id, @RequestParam("userId") Integer userId) {
		Optional<User> userOptional = userService.findById(userId);
		if (!userOptional.isPresent()) {
			throw new NotFoundException("User with id " + userId + " not found.");
		}
		Optional<Order> orderOptional = orderService.getOrder(id);
		if (!orderOptional.isPresent()) {
			throw new NotFoundException("Order with id " + id + " not found.");
		}
		return new ResponseEntity<>(orderOptional.get(), HttpStatus.OK);
	}
}
