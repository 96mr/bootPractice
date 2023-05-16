package com.spring.bootPractice.product.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.spring.bootPractice.product.entity.Category;
import com.spring.bootPractice.product.entity.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {
	private int pid;
	@NotBlank
	private String pname;
	private Category pcategory;
	@NotBlank
	private String pinfo; //blob
	@NotNull
	private int price;
	private int stock;
	private Date regdate;
	private int hit;
	
	public Product toEntity() {
		return Product.builder()
				.pname(pname)
				.pcategory(pcategory)
				.pinfo(pinfo)
				.price(price)
				.stock(stock)
				.build();
	}
}
