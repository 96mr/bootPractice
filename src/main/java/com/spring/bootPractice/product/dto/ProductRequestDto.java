package com.spring.bootPractice.product.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.spring.bootPractice.product.entity.Category;
import com.spring.bootPractice.product.entity.Product;
import com.spring.bootPractice.product.entity.ProductStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductRequestDto {
	private int pid;
	@NotBlank
	private String pname;
	private Category pcategory;
	@NotBlank
	private String pinfo; //blob
	@NotNull
	private int price;
	private ProductStatus status;
	private Date regdate;
	private int hit;
	private List<ImageResponseDto> thumbnail;
	
	@Builder
	public ProductRequestDto(int pid, String pname, Category pcategory, String pinfo,
							int price, ProductStatus status, List<ImageResponseDto> thumbnail) {
		this.pid = pid;
		this.pname = pname;
		this.pcategory = pcategory;
		this.pinfo = pinfo;
		this.price = price;
		this.status = status;
		this.thumbnail = thumbnail;
	}
	
	
	public Product toEntity() {
		return Product.builder()
				.pname(pname)
				.pcategory(pcategory)
				.pinfo(pinfo)
				.price(price)
				.status(status)
				.build();
	}
	
}
