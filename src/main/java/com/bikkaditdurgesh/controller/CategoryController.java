package com.bikkaditdurgesh.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bikkaditdurgesh.payload.ApiResponse;
import com.bikkaditdurgesh.payload.CategoryDto;
import com.bikkaditdurgesh.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/category")
@Slf4j
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	// post for createdata
	/**
	 * @author Manmohan
	 * @apiNote To createdata in database
	 * @since 1.0v
	 * @return CategoryDto
	 */
	@PostMapping("/cat")
	public ResponseEntity<CategoryDto> createcate(@Valid @RequestBody CategoryDto categoryDto) {
		log.info("Sending Request  into Service layer ");

		CategoryDto dto = this.categoryService.createCategory(categoryDto);
		log.info(" Insert Data Operation completed ");
		return new ResponseEntity<CategoryDto>(dto, HttpStatus.CREATED);

	}

	// put for update

	/**
	 * @author Manmohan
	 * @apiNote To update categoryId in database
	 * @since 1.0v
	 * @param category Id
	 * @return CategoryDto
	 */
	@PutMapping("/updcat/{CategoryId}")
	public ResponseEntity<CategoryDto> updatcate(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable Integer CategoryId) {
		log.info("Sending Request  into Service layer for CategoryId :{}", CategoryId);
		CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, CategoryId);
		log.info("Update Data Operation completed on CategoryId :{} ", CategoryId);
		return new ResponseEntity<CategoryDto>(updateCategory, HttpStatus.CREATED);
	}

	// delete
	/**
	 * @author Manmohan
	 * @apiNote To delete data in database
	 * @since 1.0v
	 * @param category Id
	 * @return ApiResponse
	 */
	@DeleteMapping("/delcat/{CategoryId}")
	public ResponseEntity<ApiResponse> deletecate(@PathVariable Integer CategoryId) {
		log.info("Sending Request  into Service layer for CategoryId :{}", CategoryId);
		this.categoryService.deleteCategory(CategoryId);
		log.info(" Delete Data Operation completed on CategoryId :{} ", CategoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("data deleted", true), HttpStatus.OK);
	}

	// get dataByid
	/**
	 * @author Manmohan
	 * @apiNote To get data by categoryid from database
	 * @since 1.0v
	 * @param category Id
	 * @return CategoryDto
	 */

	@GetMapping("/getcat/{CategoryId}")
	public ResponseEntity<CategoryDto> getcate(@PathVariable Integer CategoryId) {
		log.info("Sending Request  into Service layer for CategoryId :{}", CategoryId);
		CategoryDto categoryDto = this.categoryService.getCategory(CategoryId);
		log.info("GetdataByid  Data Operation completed on CategoryId :{} ", CategoryId);
		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
	}

	// get all data
	/**
	 * @author Manmohan
	 * @apiNote To get all data by from database
	 * @since 1.0v
	 * @return CategoryDto
	 */
	@GetMapping("/getall")
	public ResponseEntity<List<CategoryDto>> getallcate() {
		log.info("Sending Request  into Service layer ");
		List<CategoryDto> listcategoryDto = this.categoryService.getAllCategory();
		log.info(" Getall Data Operation completed  ");
		return ResponseEntity.ok(listcategoryDto);

	}
}
