package com.company.ecommerce.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.ecommerce.dto.cart.AddToCartDto;
import com.company.ecommerce.dto.cart.CartDto;
import com.company.ecommerce.exception.runtime.NotFoundException;
import com.company.ecommerce.model.Cart;
import com.company.ecommerce.model.Product;
import com.company.ecommerce.model.User;
import com.company.ecommerce.service.CartService;
import com.company.ecommerce.service.ProductService;
import com.company.ecommerce.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "CartController", description = "Operations pertaining to carts in e-commerce application")
public class CartController {

	@Autowired
	CartService cartService;

	@Autowired
	ProductService productService;

	@Autowired
	UserService userService;

	/**
	 * Method used to add product to cart by their quantity for given userId
	 * 
	 * @param addToCartDto as AddToCartDto
	 * @param userId       as Integer
	 * @return ResponseEntity<Cart>
	 * @throws NotFoundException
	 */
	@ApiOperation(value = "Create a cart and add product to cart by their quantity for given userId")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully created"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@PostMapping("/carts")
	public ResponseEntity<Cart> addToCart(@RequestBody AddToCartDto addToCartDto,
			@RequestParam("userId") Integer userId) throws NotFoundException {
		Optional<User> userOptional = userService.findById(userId);
		if (!userOptional.isPresent()) {
			throw new NotFoundException("User with id " + userId + " not found.");
		}
		Optional<Product> productOptional = productService.getProductById(addToCartDto.getProductId());
		if (!productOptional.isPresent()) {
			throw new NotFoundException("Product with id " + addToCartDto.getProductId() + " not found.");
		}
		Cart savedCart = cartService.addToCart(addToCartDto, productOptional.get(), userOptional.get());
		return new ResponseEntity<>(savedCart, HttpStatus.CREATED);
	}

	/**
	 * Method used to get cart details for given userId
	 * 
	 * @param userId as Integer
	 * @return ResponseEntity<CartDto>
	 */
	@ApiOperation(value = "Get cart details for given userId")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping("/carts")
	public ResponseEntity<CartDto> getCartItems(@RequestParam("userId") Integer userId) {
		Optional<User> userOptional = userService.findById(userId);
		if (!userOptional.isPresent()) {
			throw new NotFoundException("User with id " + userId + " not found.");
		}
		CartDto cartDto = cartService.listCartItems(userOptional.get());
		return new ResponseEntity<CartDto>(cartDto, HttpStatus.OK);
	}

	/**
	 * Method used to update cart for given userId
	 * 
	 * @param cartDto as AddToCartDto
	 * @param userId  as Integer
	 * @return ResponseEntity<Cart>
	 * @throws NotFoundException
	 */
	@ApiOperation(value = "Update cart for given userId")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@PutMapping("/carts/{cartItemId}")
	public ResponseEntity<Cart> updateCartItem(@RequestBody @Valid AddToCartDto cartDto,
			@RequestParam("userId") Integer userId) throws NotFoundException {
		Optional<User> userOptional = userService.findById(userId);
		if (!userOptional.isPresent()) {
			throw new NotFoundException("User with id " + userId + " not found.");
		}
		Optional<Product> productOptional = productService.getProductById(cartDto.getProductId());
		if (!productOptional.isPresent()) {
			throw new NotFoundException("Product with id " + cartDto.getProductId() + " not found.");
		}
		Cart cart = cartService.updateCartItem(cartDto, userOptional.get(), productOptional.get());
		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}

	/**
	 * Method used to delete cart by cart id for given userId
	 * 
	 * @param itemID as int
	 * @param userId as Integer
	 * @return ResponseEntity<HttpStatus>
	 * @throws NotFoundException
	 */
	@ApiOperation(value = "Delete cart for given cartItemId and userId")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Successfully deleted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@DeleteMapping("/carts/{cartItemId}")
	public ResponseEntity<HttpStatus> deleteCartItem(@PathVariable("cartItemId") int itemID,
			@RequestParam("userId") Integer userId) throws NotFoundException {
		Optional<User> userOptional = userService.findById(userId);
		if (!userOptional.isPresent()) {
			throw new NotFoundException("User with id " + userId + " not found.");
		}
		cartService.deleteCartItem(itemID, userId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
