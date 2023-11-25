package com.bikkaditdurgesh.service;

import com.bikkaditdurgesh.payload.CommentsDto;

public interface CommentsService {
	CommentsDto createComments(CommentsDto commentsDto, Integer postId);

	void deleteComments(Integer id);
}
