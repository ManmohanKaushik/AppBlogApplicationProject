package com.bikkaditdurgesh.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bikkaditdurgesh.constant.AppConstants;
import com.bikkaditdurgesh.entites.Role;
import com.bikkaditdurgesh.entites.User;
import com.bikkaditdurgesh.exception.ResourceNotFoundException;
import com.bikkaditdurgesh.payload.UserDto;
import com.bikkaditdurgesh.repository.RoleRepo;
import com.bikkaditdurgesh.repository.UserRepo;
import com.bikkaditdurgesh.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto userDto) {
		log.info("Sending Request into Deo layer ");
		User toUser = this.dtoToUser(userDto);
		User save = this.userRepo.save(toUser);
		log.info(" Insert Data Operation completed in Deo layer");
		return this.useTodto(save);

	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer id) {
		log.info("Sending Request into Deo layer for id:{}", id);
		User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setAbout(userDto.getAbout());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		User updateUser = this.userRepo.save(user);
		UserDto updateuseTodto = this.useTodto(updateUser);
		log.info("Update Data Operation completed in Deolayer on id:{}", id);
		return updateuseTodto;
	}

	@Override
	public UserDto getUserByid(Integer id) {
		log.info("Sending Request into Deo layer for id:{}", id);

		User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		log.info("Request Received from Deolayer for id:{}", id);
		return this.useTodto(user);
	}

	@Override
	public List<UserDto> getall() {
		log.info("Sending Request into Deo layer ");

		List<User> list = this.userRepo.findAll();
		List<UserDto> userDtolist = list.stream().map(user -> this.useTodto(user)).collect(Collectors.toList());

		log.info("Request Received Get all Data  from Deolayer ");
		return userDtolist;

	}

	@Override
	public void deleteUser(Integer id) {
		log.info("Sending Request into Deo layer for id:{}", id);

		User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		this.userRepo.delete(user);
		log.info("Request Received Delete Data  from Deolayer  id:{}", id);
	}

	public User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);

		return user;
	}

	public UserDto useTodto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);

		return userDto;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);

		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		
		user.getRoles().add(role);
		
		User newUser = this.userRepo.save(user);

		return this.modelMapper.map(newUser, UserDto.class);
	}

}
