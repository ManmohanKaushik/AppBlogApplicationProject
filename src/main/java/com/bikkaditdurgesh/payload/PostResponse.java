package com.bikkaditdurgesh.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostResponse {
 private List<PostDto> content;
 private Integer pageNumber;
 private Integer pageSize;
 
 private long totalElement;
 private Integer totalPages;
 private boolean lastPage;
}
