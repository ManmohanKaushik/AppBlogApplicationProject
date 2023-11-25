package com.bikkaditdurgesh.service;

import java.util.List;
import com.bikkaditdurgesh.payload.PostDto;
import com.bikkaditdurgesh.payload.PostResponse;

public interface PostService {
	// create post
	public PostDto createPost(PostDto postDto,Integer id,Integer categoryId);

	// update post
	public PostDto updatePost(PostDto postDto, Integer postId);

	// delete post
	void deletePost(Integer postId);

	// get all post
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy);

	// get single
	public PostDto getPostById(Integer postId);

	// get all by category

	List<PostDto> getPostByCategory(Integer categoryId);

	// get all by user
	List<PostDto> getPostByUser(Integer id);

	// search
	List<PostDto> searchPost(String keyword);

}
