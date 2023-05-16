package com.spring.bootPractice.product.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.spring.bootPractice.product.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryDto {
	private int id;
	private String name;
	private List<CategoryDto> children;
	
	public CategoryDto(Category category) {
		this.id = category.getId();
		this.name = category.getName();
		this.children = category.getChildren().stream().map(CategoryDto::new).collect(Collectors.toList());
	}
}
