package com.bikkaditdurgesh.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bikkaditdurgesh.entites.Comments;
import com.bikkaditdurgesh.entites.Post;
import com.bikkaditdurgesh.exception.ResourceNotFoundException;
import com.bikkaditdurgesh.payload.CommentsDto;
import com.bikkaditdurgesh.repository.CommentsRepo;
import com.bikkaditdurgesh.repository.PostRpo;
import com.bikkaditdurgesh.service.CommentsService;
@Service
public class CommentsServiceImpl implements CommentsService {
	@Autowired
	private PostRpo postRpo;
 
	@Autowired
	private CommentsRepo comRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentsDto createComments(CommentsDto commentsDto, Integer postId) {
		Post post = this.postRpo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","postId",postId));
		Comments comments = this.modelMapper.map(commentsDto, Comments.class);
		comments.setPost(post);
		Comments save = this.comRepo.save(comments);
		return this.modelMapper.map(save,CommentsDto.class);
	}

	@Override
	public void deleteComments(Integer id) {
		Comments comments = this.comRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Comments", "id", id));
	this.comRepo.delete(comments);
	
	}

}
