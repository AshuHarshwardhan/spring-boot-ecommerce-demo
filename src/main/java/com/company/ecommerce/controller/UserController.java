package com.company.ecommerce.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.company.ecommerce.exception.runtime.DuplicateFoundException;
import com.company.ecommerce.model.User;
import com.company.ecommerce.service.UserService;
import com.company.ecommerce.util.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "UserController", description = "Operations pertaining to users in e-commerce application")
public class UserController {

	@Autowired
	UserService userService;

	/**
	 * Method used to retrieve list of available users
	 * 
	 * @return ResponseEntity<List<User>>
	 */
	@ApiOperation(value = "Retrieve list of available users", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 204, message = "Successfully retrieved list but list is empty"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers() {
		List<User> users = userService.listUsers();
		if (users.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	/**
	 * Method used to create a user
	 * 
	 * @param category as User
	 * @return ResponseEntity<User>
	 */
	@ApiOperation(value = "Create a user")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully created"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Duplicate found") })
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		if (CommonUtil.notNull(userService.findByEmail(user.getEmail()))) {
			throw new DuplicateFoundException("User with email " + user.getEmail() + "already exists.");
		}
		return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
	}
}
