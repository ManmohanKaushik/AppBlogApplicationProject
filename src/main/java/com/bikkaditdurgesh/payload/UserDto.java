package com.bikkaditdurgesh.payload;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.bikkaditdurgesh.entites.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

	private Integer id;

	@NotEmpty
	@Size(min = 4, message = "must be 4 charecter")
	private String name;

	@Email(message = "Give valid Email address !!!!")
	private String email;

	@NotEmpty
	@Size(min = 4, max = 10, message = "Give valid password !!!")
	private String password;

	@NotEmpty(message = "Give proper message !!!")
	private String about;
	
	private Set<RoleDto> roles = new HashSet<>();

}
