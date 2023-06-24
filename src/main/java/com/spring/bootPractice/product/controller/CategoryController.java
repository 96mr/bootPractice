package com.spring.bootPractice.product.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.bootPractice.product.dto.CategoryDto;
import com.spring.bootPractice.product.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CategoryController {
	private final CategoryService categoryService;
	
	@ResponseBody
	@GetMapping(value="/api/categories")
	public ResponseEntity<List<CategoryDto>> categoryAllList(){
		List<CategoryDto> list = categoryService.getAllList();
		return ResponseEntity.ok()
							.body(list);
	}
	
	@ResponseBody
	@GetMapping(value="/api/categories/children")
	public ResponseEntity<List<CategoryDto>> categoryChildrenList(){
		List<CategoryDto> list = categoryService.getChildrenList();
		return ResponseEntity.ok()
							.body(list);
	}
}
