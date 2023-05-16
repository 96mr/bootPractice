package com.spring.bootPractice.product.dto;

import com.spring.bootPractice.product.entity.Image;
import com.spring.bootPractice.product.entity.ImageCategory;
import com.spring.bootPractice.product.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ImageRequestDto {
	private int id;
	private String org_name;
	private String save_name;
	private String path;
	private String extension;
	private ImageCategory category;
	private Product pid;
	
	@Builder
	public ImageRequestDto(String org_name, String save_name, String path, String extension, ImageCategory category, Product pid) {
		this.org_name = org_name;
		this.save_name = save_name;
		this.path = path;
		this.extension = extension;
		this.category = category;
		this.pid = pid;
	}
	
	public Image toEntity() {
		return Image.builder()
				.org_name(org_name)
				.save_name(save_name)
				.path(path)
				.extension(extension)
				.category(category)
				.pid(pid)
				.build();
	}
}
