package com.bikkaditdurgesh.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bikkaditdurgesh.constant.MessageConstants;
import com.bikkaditdurgesh.payload.ApiResponse;
import com.bikkaditdurgesh.payload.UserDto;
import com.bikkaditdurgesh.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
	@Autowired
	private UserService userService;

	/**
	 * @author Manmohan Sharma
	 * @apiNote To create data in database
	 * @param userDto
	 * @since 1.0v
	 * @return UserDto
	 */
	@PostMapping("/adduser")
	public ResponseEntity<UserDto> createusers(@Valid @RequestBody UserDto userDto) {
		log.info("Sending Request into Service layer");
		UserDto createUserDto = this.userService.createUser(userDto);
		log.info(" Insert Data Operation completed");
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);

	}

	/**
	 * @author Manmohan
	 * @apiNote To update data in database
	 * @param id
	 * @since 1.0v
	 * @return UserDto
	 */
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer id) {
		log.info("Sending Request  into Service layer for id  :{}", id);
		UserDto updateUser = this.userService.updateUser(userDto, id);
		log.info("Update Data Operation completed on id :{} ", id);
		return ResponseEntity.ok(updateUser);

	}

	/**
	 * @apiNote Data deleted Successfully in database
	 * @author Manmohan
	 * @param id
	 * @since 1.0v
	 * @return ApiResponse
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delid/{id}")
	
	public ResponseEntity<ApiResponse> deleteuser(@PathVariable Integer id) {
		log.info("Sending Request into Service layer for id:{}", id);
		this.userService.deleteUser(id);
		log.info(" Delete  Data Operation completed on id:{}", id);
		return new ResponseEntity<ApiResponse>(new ApiResponse(MessageConstants.DELETE_STRING, true), HttpStatus.OK);
	}

	/**
	 * @author Manmohan
	 * @apiNote To get alldata from database
	 * @since 1.0v
	 * @return UserDto
	 */
	@GetMapping("/getall")
	public ResponseEntity<List<UserDto>> getalluser() {
		log.info("Sending Request into Service layer");
		List<UserDto> list = this.userService.getall();
		log.info(" Get all Data Operation completed");
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * @author Manmohan
	 * @apiNote To get data by id from database
	 * @since 1.0v
	 * @param id
	 * @return UserDto
	 */
	@GetMapping("/getid/{id}")
	public ResponseEntity<UserDto> getSingleuser(@PathVariable Integer id) {
		log.info("Sending Request into Service layer for id:{}", id);
		UserDto userByid = this.userService.getUserByid(id);
		log.info("Get ById Data Operation completed on id:{}", id);
		return new ResponseEntity<>(userByid, HttpStatus.OK);
	}

}
