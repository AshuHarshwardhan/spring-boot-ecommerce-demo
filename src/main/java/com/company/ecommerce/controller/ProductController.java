package com.company.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.company.ecommerce.dto.product.ProductDto;
import com.company.ecommerce.exception.runtime.NotFoundException;
import com.company.ecommerce.model.Category;
import com.company.ecommerce.model.Product;
import com.company.ecommerce.service.CategoryService;
import com.company.ecommerce.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "ProductController", description = "Operations pertaining to products in e-commerce application")
public class ProductController {

	@Autowired
	ProductService productService;

	@Autowired
	CategoryService categoryService;

	/**
	 * Method used to retrieve list of available products
	 * 
	 * @return ResponseEntity<List<ProductDto>>
	 */
	@ApiOperation(value = "Retrieve list of available products", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 204, message = "Successfully retrieved list but list is empty"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping("/products")
	public ResponseEntity<List<ProductDto>> getProducts() {
		List<ProductDto> productDtos = productService.listProducts();
		if (productDtos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(productDtos, HttpStatus.OK);
	}

	/**
	 * Method used to create a product
	 * 
	 * @param productDto as ProductDto
	 * @return ResponseEntity<Product>
	 */
	@ApiOperation(value = "Create a product")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully created"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Duplicate found") })
	@PostMapping("/products")
	public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductDto productDto) {
		Optional<Category> optionalCategory = categoryService.findById(productDto.getCategoryId());
		if (!optionalCategory.isPresent()) {
			throw new NotFoundException("Category with id " + productDto.getCategoryId() + " not found.");
		}
		Category category = optionalCategory.get();
		Product savedProduct = productService.addProduct(productDto, category);
		return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
	}

	/**
	 * Method used to update a product
	 * 
	 * @param productId  as Integer
	 * @param productDto as ProductDto
	 * @return ResponseEntity<Product>
	 */
	@ApiOperation(value = "Create a product")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully created"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Duplicate found") })
	@PutMapping("/products/{productID}")
	public ResponseEntity<Product> updateProduct(@PathVariable("productID") Integer productID,
			@RequestBody @Valid ProductDto productDto) {
		Optional<Category> optionalCategory = categoryService.findById(productDto.getCategoryId());
		if (!optionalCategory.isPresent()) {
			throw new NotFoundException("Category with id " + productDto.getCategoryId() + " not found.");
		}
		Category category = optionalCategory.get();
		return new ResponseEntity<>(productService.updateProduct(productID, productDto, category), HttpStatus.OK);
	}
}
