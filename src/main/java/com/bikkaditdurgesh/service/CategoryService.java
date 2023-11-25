package com.bikkaditdurgesh.service;

import java.util.List;

import com.bikkaditdurgesh.payload.CategoryDto;

public interface CategoryService {
	// create data
	public CategoryDto createCategory(CategoryDto categoryDto);

	// update data
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

	// delete data
	public void deleteCategory(Integer categoryId);

	// get data
	public CategoryDto getCategory(Integer categoryId);

	// getAll data
	List<CategoryDto> getAllCategory();
}
