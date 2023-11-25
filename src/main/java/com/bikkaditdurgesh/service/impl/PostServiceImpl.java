package com.bikkaditdurgesh.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.bikkaditdurgesh.entites.Category;
import com.bikkaditdurgesh.entites.Post;
import com.bikkaditdurgesh.entites.User;
import com.bikkaditdurgesh.exception.ResourceNotFoundException;
import com.bikkaditdurgesh.payload.PostDto;
import com.bikkaditdurgesh.payload.PostResponse;
import com.bikkaditdurgesh.repository.CategoryRpo;
import com.bikkaditdurgesh.repository.PostRpo;
import com.bikkaditdurgesh.repository.UserRepo;
import com.bikkaditdurgesh.service.PostService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;

@Service
@Slf4j
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRpo postRpo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRpo categoryRpo;

	@Override
	public PostDto createPost(PostDto postDto, Integer id, Integer categoryId) {
		log.info("Sending Request into Deo layer ");
		User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

		Category category = this.categoryRpo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryid", categoryId));

		Post map = this.modelMapper.map(postDto, Post.class);
		map.setImageName("default.png");
		map.setAddDate(new Date());
		map.setCategory(category);
		map.setUser(user);
		Post newsave = this.postRpo.save(map);
		log.info(" Insert Data Operation completed in Deo layer");
		return this.modelMapper.map(newsave, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		log.info("Sending Request into Deo layer for postId:{}", postId);
		Post post = this.postRpo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		post.setContent(postDto.getContent());
		post.setTitle(postDto.getTitle());
		post.setImageName(postDto.getImageName());
		Post updatePost = this.postRpo.save(post);
		log.info(" Update Data Operation completed in Deolayer on postId:{}", postId);
		return this.modelMapper.map(updatePost, PostDto.class);

	}

	@Override
	public void deletePost(Integer postId) {
		log.info("Sending Request into Deo layer for postId:{}", postId);
		Post post = this.postRpo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		this.postRpo.delete(post);
		log.info("Request Received  from Deolayer  postId:{}", postId);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy) {

		Pageable p = PageRequest.of(pageSize, pageSize, Sort.by(sortBy));
		log.info("Sending Request into Deo layer ");
		Page<Post> page = this.postRpo.findAll(p);
		List<Post> allPost = page.getContent();

		List<PostDto> collectPostDto = allPost.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(collectPostDto);
		postResponse.setPageNumber(page.getNumber());
		postResponse.setPageSize(page.getSize());
		postResponse.setTotalElement(page.getTotalElements());
		postResponse.setTotalPages(page.getTotalPages());
		postResponse.setLastPage(page.isLast());
		log.info("Request Received Get all Data Operation from Deolayer ");
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		log.info("Sending Request into Deo layer for postId:{}", postId);
		Post post = this.postRpo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		PostDto postDto = this.modelMapper.map(post, PostDto.class);
		log.info("Request Received   from Deolayer for postId:{}", postId);
		return postDto;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		log.info("Sending Request into Deo layer for categoryId:{}", categoryId);
		Category category = this.categoryRpo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		List<Post> posts = this.postRpo.findByCategory(category);
		List<PostDto> collect = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		log.info("Request Received  from Deolayer for categoryId:{}", categoryId);
		return collect;
	}

	@Override
	public List<PostDto> getPostByUser(Integer id) {
		log.info("Sending Request into Deo layer for id:{}", id);
		User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		List<Post> posts = this.postRpo.findByUser(user);
		List<PostDto> collectPostDto = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		log.info("Request Received from Deolayer for id:{}", id);
		return collectPostDto;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		log.info("Sending Request into Deo layer ");
		List<Post> posts = this.postRpo.findByTitleContaining(keyword);
		List<PostDto> collectPostDto = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		log.info("Request  Received  from Deolayer ");

		return collectPostDto;
	}

}
