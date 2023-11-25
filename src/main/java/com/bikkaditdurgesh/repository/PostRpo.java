package com.bikkaditdurgesh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bikkaditdurgesh.entites.Category;
import com.bikkaditdurgesh.entites.Post;
import com.bikkaditdurgesh.entites.User;

public interface PostRpo extends JpaRepository<Post, Integer> {

	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	List<Post> findByTitleContaining(String title);
	}
