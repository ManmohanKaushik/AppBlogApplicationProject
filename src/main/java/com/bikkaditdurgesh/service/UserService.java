package com.bikkaditdurgesh.service;

import java.util.List;

import com.bikkaditdurgesh.payload.UserDto;

public interface UserService {
	UserDto registerNewUser(UserDto userDto);
	
	UserDto createUser(UserDto userDto);

	UserDto updateUser(UserDto userDto, Integer id);

	UserDto getUserByid(Integer id);

	List<UserDto> getall();

	void deleteUser(Integer id);

}
