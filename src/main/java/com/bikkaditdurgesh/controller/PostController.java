package com.bikkaditdurgesh.controller;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bikkaditdurgesh.constant.AppConstants;
import com.bikkaditdurgesh.payload.ApiResponse;
import com.bikkaditdurgesh.payload.PostDto;
import com.bikkaditdurgesh.payload.PostResponse;
import com.bikkaditdurgesh.service.FileService;
import com.bikkaditdurgesh.service.PostService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class PostController {
	@Autowired
	private PostService postService;
	@Autowired
	private FileService fileService;
	@Value("${project.image}")
	private String path;

	/**
	 * @author Manmohan
	 * @apiNote To create data by categoryId in database
	 * @since 1.0v
	 * @param categoryId
	 * @return PostDto
	 */
	@PostMapping("/post/user/{id}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer id,
			@PathVariable Integer categoryId) {
		log.info("Sending Request  into Service layer ");
		PostDto createPost = this.postService.createPost(postDto, id, categoryId);
		log.info(" Insert Data Operation completed ");
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);

	}

	// get by userpost

	/**
	 * @author Manmohan
	 * @apiNote To get data by userId from database
	 * @since 1.0v
	 * @param userid
	 * @return PostDto
	 */
	@GetMapping("/user/{id}/posts")
	public ResponseEntity<List<PostDto>> getByUserPost(@PathVariable Integer id) {
		log.info("Sending Request  into Service layer for Id :{}", id);
		List<PostDto> list = this.postService.getPostByUser(id);
		log.info("GetdataByid Data Operation completed on Id :{}", id);
		return new ResponseEntity<List<PostDto>>(list, HttpStatus.OK);

	}

	/**
	 * @author Manmohan
	 * @apiNote To get data by categoryId from database
	 * @since 1.0v
	 * @param categoryId
	 * @return PostDto
	 */
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getByCategoryPost(@PathVariable Integer categoryId) {
		log.info("Sending Request  into Service layer for CategoryId :{}", categoryId);
		List<PostDto> list = this.postService.getPostByCategory(categoryId);
		log.info("GetdataByid Data Operation completed on CategoryId :{} ", categoryId);
		return new ResponseEntity<List<PostDto>>(list, HttpStatus.OK);

	}

	@GetMapping("/postall")

	/**
	 * @author Manmohan
	 * @apiNote To getall data from database
	 * @since 1.0v
	 * @return PostResponse
	 */
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy) {
		log.info("Sending Request  into Service layer ");
		PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy);
		log.info("Getall Data Operation completed  ");
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);

	}

	/**
	 * @author Manmohan
	 * @apiNote To getpostById by postId from database
	 * @since 1.0v
	 * @param postId
	 * @return PostDto
	 */
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getpostById(@PathVariable Integer postId) {
		log.info("Sending Request  into Service layer for postId :{}", postId);
		PostDto postById = this.postService.getPostById(postId);
		log.info("GetdataByid Data Operation completed on postId :{} ", postId);
		return new ResponseEntity<PostDto>(postById, HttpStatus.OK);

	}

	/**
	 * @author Manmohan
	 * @apiNote To delete by postId from database
	 * @since 1.0v
	 * @param postId
	 * @return PostDto
	 */
	@DeleteMapping("/delete/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {
		log.info("Sending Request  into Service layer for postId :{}", postId);
		this.postService.deletePost(postId);
		log.info("DeleteByid Data Operation completed on postId :{} ", postId);
		return new ApiResponse("Delete data", true);

	}

	/**
	 * @author Manmohan
	 * @apiNote To update by postId in database
	 * @since 1.0v
	 * @param postId
	 * @return PostDto
	 */
	@PutMapping("/update/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		log.info("Sending Request  into Service layer for postId :{}", postId);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		log.info(" Update Data Operation completed on postId :{} ", postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}

	// searching

	/**
	 * @author Manmohan
	 * @apiNote To search by keyword from database
	 * @since 1.0v
	 * @param keyword
	 * @return PostDto
	 */
	@GetMapping("/post/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostBytitle(@PathVariable("keyword") String keyword) {
		log.info("Sending Request  into Service layer ");
		List<PostDto> searchPost = this.postService.searchPost(keyword);
		log.info(" SearchPostBytitle Data   Operation completed  ");
		return new ResponseEntity<List<PostDto>>(searchPost, HttpStatus.OK);

	}

	@PostMapping("/post/image/{postId}")
	public ResponseEntity<PostDto> uploadPostimage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException {
		PostDto postDto = this.postService.getPostById(postId);
		String filename = this.fileService.uploadImage(path, image);

		postDto.setImageName(filename);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}
	@GetMapping(value="/post/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable ("imageName")String imageName,HttpServletResponse response ) throws IOException 
	{
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
