package com.spring.bootPractice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="MEMBER", schema="EX4")
public class Member {
	@Id
	@Column
	@Pattern(regexp="^[a-zA-z0-9]{4,20}$", message="4~20자리 영문자 또는 숫자로 입력해주세요")
	private String id;
	@Column(nullable = false)
	@Pattern(regexp="^[a-zA-z0-9]{8,30}$", message="8~30자리 영문자와 숫자를 입력해주세요")
	private String password;
	@Column(nullable = false)
	private String name;
	@Column
	@Email
	private String email;
	@Column
	private Date regdate;
	@Column
	private String auth;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", password=" + password + ", name=" + name + ", email=" + email + ", regdate="
				+ regdate + ", auth=" + auth + "]";
	}
	
}
