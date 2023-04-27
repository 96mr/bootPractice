package com.spring.bootPractice.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.spring.bootPractice.entity.MemberDto;

@RunWith(SpringRunner.class)
@SpringBootTest
class MemberServiceTest {

	@Autowired
	MemberService memberService;
	
	@Test
	void test() {
		MemberDto dto = new MemberDto();
		dto.setId("exmkl");
		dto.setPassword("qwer1234");
		dto.setName("aaa");
		dto.setEmail("Asfdd@gmail.com");
		dto.setAuth("ROLE_USER");
		memberService.save(dto);
	}

}
