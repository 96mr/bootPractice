package com.spring.bootPractice.member.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.spring.bootPractice.member.entity.Member;
import com.spring.bootPractice.member.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {
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
	private Role auth;

	public Member toEntity() {
		Member member = Member.builder()
				.id(id)
				.password(password)
				.name(name)
				.email(email)
				.regdate(regdate)
				.auth(auth)
				.build();
		return member;
	}
}
