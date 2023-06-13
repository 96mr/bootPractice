package com.spring.bootPractice.product.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.spring.bootPractice.product.entity.Product;

import lombok.Getter;

@Getter
public class ProductResponseDto {
	private int pid;
	private String pname;
	private int pcategory;
	private String pinfo; //blob
	private int price;
	private int stock;
	private Date regdate;
	private int hit;
	private List<ImageResponseDto> thumbnail;
	
	public ProductResponseDto(Product product) {
		this.pid = product.getPid();
		this.pname = product.getPname();
		this.pcategory = product.getPcategory().getId();
		this.pinfo = product.getPinfo();
		this.price = product.getPrice();
		this.stock = product.getStock();
		this.regdate = product.getRegdate();
		this.hit = product.getHit();
		this.thumbnail = product.getThumbnail().stream()
												.map(ImageResponseDto::new)
												.collect(Collectors.toList());
	}
}
