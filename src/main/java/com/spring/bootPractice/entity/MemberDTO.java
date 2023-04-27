package com.spring.bootPractice.entity;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
	@NotBlank
	@Pattern(regexp="^[a-zA-z0-9]{4,20}$", message="4~20자리의 영문자와 숫자로 입력해주세요")
	private String id;
	@NotBlank
	@Pattern(regexp="^[a-zA-z0-9]{8,45}$", message="8~45자리의 영문자와 숫자를 입력해주세요")
	private String password;
	@NotBlank(message="이름을 입력해주세요")
	private String name;
	@NotBlank(message="이메일을 입력해주세요")
	@Email
	private String email;
	private Date regdate;
	private String auth;

	@Builder
	public Member toEntity() {
		Member member = Member.builder()
				.id(id)
				.password(password)
				.name(name)
				.email(email)
				.regdate(regdate)
				.auth("ROLE_GUEST")
				.build();
		return member;
	}

}
