package com.company.ecommerce.controller;

import java.util.List;

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

import com.company.ecommerce.exception.runtime.DuplicateFoundException;
import com.company.ecommerce.exception.runtime.NotFoundException;
import com.company.ecommerce.model.Category;
import com.company.ecommerce.service.CategoryService;
import com.company.ecommerce.util.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "CategoryController", description = "Operations pertaining to categories in e-commerce application")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	/**
	 * Method used to retrieve list of available categories
	 * 
	 * @return ResponseEntity<List<Category>>
	 */
	@ApiOperation(value = "Retrieve list of available categories", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 204, message = "Successfully retrieved list but list is empty"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping("/categories")
	public ResponseEntity<List<Category>> getCategories() {
		List<Category> categories = categoryService.listCategories();
		if (categories.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}

	/**
	 * Method used to create a category
	 * 
	 * @param category as Category
	 * @return ResponseEntity<Category>
	 */
	@ApiOperation(value = "Create a category")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully created"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Duplicate found") })
	@PostMapping("/categories")
	public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
		if (CommonUtil.notNull(categoryService.findByCategoryName(category.getCategoryName()))) {
			throw new DuplicateFoundException("Category already exists: " + category.toString());
		}
		Category savedCategory = categoryService.createCategory(category);
		return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
	}

	/**
	 * Method used to update a category
	 * 
	 * @param categoryID as Integer
	 * @param category   as Category
	 * @return ResponseEntity<Category>
	 */
	@ApiOperation(value = "Update a category")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@PutMapping("/categories/{categoryID}")
	public ResponseEntity<Category> updateCategory(@PathVariable("categoryID") Integer categoryID,
			@Valid @RequestBody Category category) {
		if (CommonUtil.notNull(categoryService.findById(categoryID))) {
			return new ResponseEntity<>(categoryService.updateCategory(categoryID, category), HttpStatus.OK);
		}
		throw new NotFoundException("Category with id " + categoryID + " not found.");
	}
}
