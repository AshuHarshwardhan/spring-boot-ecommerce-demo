package com.company.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.ecommerce.model.Category;
import com.company.ecommerce.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	public List<Category> listCategories() {
		return repository.findAll();
	}

	public Category createCategory(Category category) {
		return repository.save(category);
	}

	public Category findByCategoryName(String categoryName) {
		return repository.findByCategoryName(categoryName);
	}

	public Optional<Category> findById(Integer categoryId) {
		return repository.findById(categoryId);
	}

	public Category updateCategory(Integer categoryID, Category newCategory) {
		Category category = repository.findById(categoryID).get();
		category.setCategoryName(newCategory.getCategoryName());
		category.setDescription(newCategory.getDescription());
		category.setProducts(newCategory.getProducts());
		category.setImageUrl(newCategory.getImageUrl());

		return repository.save(category);
	}
}
