package com.bikkaditdurgesh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bikkaditdurgesh.entites.Comments;

public interface CommentsRepo extends JpaRepository<Comments, Integer> {

}
