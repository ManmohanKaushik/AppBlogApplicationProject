package com.bikkaditdurgesh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bikkaditdurgesh.payload.JwtAuthRequest;
import com.bikkaditdurgesh.payload.JwtAuthenticationResponse;
import com.bikkaditdurgesh.payload.UserDto;
import com.bikkaditdurgesh.security.JwtTokenHelper;
import com.bikkaditdurgesh.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthenticationResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {

		this.authenticate(request.getUsername(), request.getPassword());

		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());

		String token = this.jwtTokenHelper.generateToken(userDetails);

		JwtAuthenticationResponse response = new JwtAuthenticationResponse();

		response.setToken(token);

		return new ResponseEntity<JwtAuthenticationResponse>(response, HttpStatus.OK);

	}

	private void authenticate(String username, String password) throws Exception {
		UsernamePasswordAuthenticationToken authenticToken = new UsernamePasswordAuthenticationToken(username,
				password);
		try {
			this.authenticationManager.authenticate(authenticToken);
		} catch (BadCredentialsException e) {
			System.out.println("Invalid Details");
			// throw new ApiException("Your username or passward is Invalid ");
		}

	}

//register new user api
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto useeDto) {
		UserDto registerNewUser = this.userService.registerNewUser(useeDto);

		return new ResponseEntity<UserDto>(registerNewUser, HttpStatus.CREATED);

	}

}
