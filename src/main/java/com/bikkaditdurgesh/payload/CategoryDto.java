package com.bikkaditdurgesh.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CategoryDto {
	private Integer categoryId;

	@NotEmpty
	@Size(min = 4, message = "Charecter min range is 4")
	private String categoryDescription;

	@NotEmpty
	@Size(min = 4, message = "Charecter min range is 4")
	private String categoryTitle;
}
