package com.spring.bootPractice.product.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;

@Getter
@SequenceGenerator(name="PRODUCT_GEN_SEQ", sequenceName="PRODUCT_SEQ", allocationSize=1)
@Entity
@Table(name="PRODUCT", schema="EX4")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="PRODUCT_GEN_SEQ")
	private int pid;
	private String pname;
	private String pcategory;
	@Lob
	private String pinfo; //blob
	private int price;
	private int stock;
	@Temporal(TemporalType.DATE)
	private Date regdate;
	private int hit;
}
