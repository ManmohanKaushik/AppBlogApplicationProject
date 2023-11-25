package com.bikkaditdurgesh.payload;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDto {

	private Integer postId;

	@Column(length = 100, nullable = false)
	private String title;

	@Column(length = 2000, nullable = false)
	private String content;

	private Date addDate;

	private String imageName;

	private CategoryDto category;

	private UserDto user;
	
	private Set<CommentsDto> comments=new HashSet<>();
}
