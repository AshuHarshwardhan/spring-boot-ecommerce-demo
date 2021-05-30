package com.company.ecommerce.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.ecommerce.dto.cart.AddToCartDto;
import com.company.ecommerce.dto.cart.CartDto;
import com.company.ecommerce.dto.cart.CartItemDto;
import com.company.ecommerce.exception.runtime.NotFoundException;
import com.company.ecommerce.model.Cart;
import com.company.ecommerce.model.Product;
import com.company.ecommerce.model.User;
import com.company.ecommerce.repository.CartRepository;

@Service
public class CartService {

	@Autowired
	CartRepository repository;

	public Cart addToCart(AddToCartDto addToCartDto, Product product, User user) {
		Cart cart = new Cart(product, addToCartDto.getQuantity(), user);
		return repository.save(cart);
	}

	private CartItemDto getDtoFromCart(Cart cart) {
		CartItemDto cartItemDto = new CartItemDto(cart);
		return cartItemDto;
	}

	public CartDto listCartItems(User user) {
		List<Cart> cartList = repository.findAllByUserOrderByCreatedDateDesc(user);
		List<CartItemDto> cartItems = new ArrayList<>();
		for (Cart cart : cartList) {
			CartItemDto cartItemDto = getDtoFromCart(cart);
			cartItems.add(cartItemDto);
		}
		double totalCost = 0;
		for (CartItemDto cartItemDto : cartItems) {
			totalCost += (cartItemDto.getProduct().getPrice() * cartItemDto.getQuantity());
		}
		CartDto cartDto = new CartDto(cartItems, totalCost);
		return cartDto;
	}

	public Cart updateCartItem(AddToCartDto cartDto, User user, Product product) {
		Optional<Cart> cartOptional = repository.findById(cartDto.getId());
		if (cartOptional.isPresent()) {
			Cart cart = cartOptional.get();
			cart.setQuantity(cartDto.getQuantity());
			cart.setCreatedDate(new Date());
			return repository.save(cart);
		}else {
			throw new NotFoundException("Cart with id " + cartDto.getId() + " not found.");
		}
	}

	public void deleteCartItem(int id, int userId) throws NotFoundException {
		if (!repository.existsById(id))
			throw new NotFoundException("Cart with id " + id + " not found.");
		repository.deleteById(id);

	}

	public void deleteCartItems(int userId) {
		repository.deleteAll();
	}

	public void deleteUserCartItems(User user) {
		repository.deleteByUser(user);
	}
}
