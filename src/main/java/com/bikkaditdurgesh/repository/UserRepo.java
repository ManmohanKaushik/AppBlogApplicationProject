package com.bikkaditdurgesh.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bikkaditdurgesh.entites.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	Optional<User> findByEmail(String email);
}
