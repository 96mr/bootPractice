package com.spring.bootPractice.product.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@SequenceGenerator(name="PRODUCT_GEN_SEQ", sequenceName="PRODUCT_SEQ", allocationSize=1)
@DynamicInsert
@Entity
@Table(name="PRODUCT", schema="EX4")
public class Product {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="PRODUCT_GEN_SEQ")
	private int pid;
	@Column
	private String pname;
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name ="pcategory")
	private Category pcategory;
	@Lob
	private String pinfo; //blob
	private int price;
	@Enumerated(EnumType.STRING)
	private ProductStatus status;
	@Temporal(TemporalType.DATE)
	private Date regdate;
	private int hit;	
	@OneToMany(mappedBy="pid", fetch = FetchType.EAGER)
	private List<Image> thumbnail = new ArrayList<>();
	
	@Builder
	public Product(String pname, Category pcategory, String pinfo, int price, ProductStatus status) {
		this.pname = pname;
		this.pcategory = pcategory;
		this.pinfo = pinfo;
		this.price = price;
		this.status = status;
	}
	
	public void update(int hit) {
		this.hit = hit;
	}
	
	public void update(String pname, String pinfo, int price) {
		this.pname = pname;
		this.pinfo = pinfo;
		this.price = price;
	}
	
	public void update(ProductStatus status) {
		this.status = status;
	}
}
