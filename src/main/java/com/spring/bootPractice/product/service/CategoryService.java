package com.spring.bootPractice.product.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.bootPractice.product.dto.CategoryDto;
import com.spring.bootPractice.product.entity.Category;
import com.spring.bootPractice.product.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	private final CategoryRepository categoryRepository;
	
	@Transactional
	public List<CategoryDto> allList(){
		List<Category> list = categoryRepository.findByParentIsNullOrderByIdAsc();
		return list.stream().map(CategoryDto::new).collect(Collectors.toList());
	}
	
	@Transactional
	public List<CategoryDto> childrenList(){
		List<Category> list = categoryRepository.findByParentNotNullOrderByIdAsc();
		return list.stream().map(CategoryDto::new).collect(Collectors.toList());
	}
}
