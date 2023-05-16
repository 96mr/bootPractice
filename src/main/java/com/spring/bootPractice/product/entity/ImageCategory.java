package com.spring.bootPractice.product.entity;

import lombok.Getter;

@Getter
public enum ImageCategory {
	PRODUCT("제품"),
	COMMENT("댓글");

	String category;
	
	ImageCategory(String category){
		this.category = category;
	}
}
