package com.spring.bootPractice.product.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@SequenceGenerator(name="IMAGE_GEN_SEQ", sequenceName="IMAGE_SEQ", allocationSize=1)
@Entity
@Table(name="IMAGE", schema="EX4")
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IMAGE_GEN_SEQ")
	private int id;
	private String org_name;
	private String save_name;
	private String path;
	private String extension;
	@Enumerated(EnumType.STRING)
	private ImageCategory category;
	private int pid;
	
	@Builder
	public Image(String org_name, String save_name, String path, String extension, ImageCategory category, int pid) {
		this.org_name = org_name;
		this.save_name = save_name;
		this.path = path;
		this.extension = extension;
		this.category = category;
		this.pid = pid;
	}
}
