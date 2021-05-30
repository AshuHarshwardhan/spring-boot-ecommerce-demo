package com.company.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.ecommerce.dto.product.ProductDto;
import com.company.ecommerce.model.Category;
import com.company.ecommerce.model.Product;
import com.company.ecommerce.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	private ProductDto getDtoFromProduct(Product product) {
		ProductDto productDto = new ProductDto(product);
		return productDto;
	}

	private Product getProductFromDto(ProductDto productDto, Category category) {
		Product product = new Product(productDto, category);
		return product;
	}

	public List<ProductDto> listProducts() {
		List<Product> products = repository.findAll();
		List<ProductDto> productDtos = new ArrayList<>();
		for (Product product : products) {
			ProductDto productDto = getDtoFromProduct(product);
			productDtos.add(productDto);
		}
		return productDtos;
	}

	public Product addProduct(ProductDto productDto, Category category) {
		Product product = getProductFromDto(productDto, category);
		return repository.save(product);
	}

	public Product updateProduct(Integer productID, ProductDto productDto, Category category) {
		Product product = getProductFromDto(productDto, category);
		product.setId(productID);
		return repository.save(product);
	}

	public Optional<Product> getProductById(Integer productId) {
		return repository.findById(productId);
	}
}
