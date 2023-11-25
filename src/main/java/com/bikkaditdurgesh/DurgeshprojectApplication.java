package com.bikkaditdurgesh;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bikkaditdurgesh.constant.AppConstants;
import com.bikkaditdurgesh.entites.Role;
import com.bikkaditdurgesh.repository.RoleRepo;

@SpringBootApplication
public class DurgeshprojectApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	
	public static void main(String[] args) {
		SpringApplication.run(DurgeshprojectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("theto45"));
		
		try {
		Role role1=new Role();
		role1.setRoleId(AppConstants.ADMIN_USER);
		role1.setName("ADMIN_USER");
		
		Role role2=new Role();
		role2.setRoleId(AppConstants.NORMAL_USER);
		role2.setName("NORMAL_USER");
		
		List<Role> role = List.of(role1,role2);
		List<Role> result = this.roleRepo.saveAll(role);
		
		result.forEach(r->{
			System.out.println(r.getName());
		});}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
}
