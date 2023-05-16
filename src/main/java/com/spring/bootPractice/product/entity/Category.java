package com.spring.bootPractice.product.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Entity
@Table(name="CATEGORY", schema="EX4")
public class Category {
	@Id
	private int id;
	@Column(name="name")
	private String name;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="parent")
	private Category parent;
	@OneToMany(mappedBy="parent")
	private List<Category> children = new ArrayList<>();
	
	@Builder
	public Category(String name, Category parent) {
		this.name = name;
		this.parent = parent;
	}
	
}
