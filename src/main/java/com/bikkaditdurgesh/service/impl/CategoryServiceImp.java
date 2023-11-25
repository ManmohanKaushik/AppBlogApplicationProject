package com.bikkaditdurgesh.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bikkaditdurgesh.entites.Category;
import com.bikkaditdurgesh.exception.ResourceNotFoundException;
import com.bikkaditdurgesh.payload.CategoryDto;
import com.bikkaditdurgesh.repository.CategoryRpo;
import com.bikkaditdurgesh.service.CategoryService;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class CategoryServiceImp  implements CategoryService{

	 @Autowired
		private CategoryRpo categoryRpo;
		@Autowired
	     private ModelMapper modelMapper;
		
		@Override
		public CategoryDto createCategory(CategoryDto categoryDto) {
			log.info("Sending Request into Deo layer ");
			Category cat = this.modelMapper.map(categoryDto,Category.class);
			cat.setCategoryDescription(categoryDto.getCategoryDescription());
			cat.setCategoryTitle(categoryDto.getCategoryTitle());
			Category addcat = this.categoryRpo.save(cat);
			log.info(" Insert Data Operation completed in Deo layer");
			return this.modelMapper.map(addcat, CategoryDto.class);
		}

		@Override
		public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
			log.info("Sending Request into Deo layer for categoryId:{}", categoryId);
			Category cat= this.categoryRpo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
			cat.setCategoryDescription(categoryDto.getCategoryDescription());
			cat.setCategoryTitle(categoryDto.getCategoryTitle());
			Category updatecat = this.categoryRpo.save(cat);
			log.info("Update Data Operation completed in Deolayer on categoryId:{}", categoryId);
			return this.modelMapper.map(updatecat, CategoryDto.class);
		}

		@Override
		public void deleteCategory(Integer categoryId) {
			log.info("Sending Request into Deo layer for categoryId:{}", categoryId);
			Category cat=this.categoryRpo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
			//cat.setCategorydescription(categoryDto.);
			this.categoryRpo.delete(cat);
			log.info(" Request Received   from Deolayer  categoryId:{}", categoryId);
			
		}

		@Override
		public CategoryDto getCategory(Integer categoryId) {
			log.info("Sending Request into Deo layer for categoryId:{}", categoryId);
			Category cat =this.categoryRpo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId", categoryId));
			log.info("Request Received   from Deolayer for categoryId:{}", categoryId);
			return this.modelMapper.map(cat, CategoryDto.class);
		}

		@Override
		public List<CategoryDto> getAllCategory() {
			log.info("Sending Request into Deo layer ");
			List<Category> listcategory = this.categoryRpo.findAll();
			List<CategoryDto> listCategoryDto = listcategory.stream().map((cat)->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
			log.info("Request Received from Deolayer ");
			return listCategoryDto;
		}

}
