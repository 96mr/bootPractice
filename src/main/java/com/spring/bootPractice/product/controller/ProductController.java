package com.spring.bootPractice.product.controller;

import org.springframework.stereotype.Controller;

import com.spring.bootPractice.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductService productService;

}
