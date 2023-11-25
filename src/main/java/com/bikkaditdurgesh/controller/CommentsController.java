package com.bikkaditdurgesh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bikkaditdurgesh.payload.ApiResponse;
import com.bikkaditdurgesh.payload.CommentsDto;
import com.bikkaditdurgesh.service.CommentsService;

@RestController
@RequestMapping("/api")
public class CommentsController {
	@Autowired
	private CommentsService comService;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentsDto> createComments(@RequestBody CommentsDto commentsDto,@PathVariable Integer postId)
	{
		CommentsDto createComments = this.comService.createComments(commentsDto, postId);
		return new ResponseEntity<CommentsDto>(createComments,HttpStatus.CREATED);
		
	}
	@DeleteMapping("/deletecomments/{id}")
	public ResponseEntity<ApiResponse> deleteComments(@PathVariable Integer id)
	{
		this.comService.deleteComments(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("data deleted successfully", true), HttpStatus.OK);
		
	}

}
