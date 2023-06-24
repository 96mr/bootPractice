package com.spring.bootPractice.product.dto;

import com.spring.bootPractice.product.entity.Image;
import com.spring.bootPractice.product.entity.ImageCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ImageResponseDto {
	private int id;
	private String org_name;
	private String save_name;
	private String path;
	private String extension;
	private ImageCategory category;
	private int pid;
	
	/* Entity -> Dto */
	public ImageResponseDto(Image image) {
		this.id = image.getId();
		this.org_name = image.getOrg_name();
		this.save_name = image.getSave_name();
		this.path = image.getPath();
		this.extension = image.getExtension();
		this.category = image.getCategory();
		this.pid = image.getPid();
	}
}
