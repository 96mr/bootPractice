package com.spring.bootPractice.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.spring.bootPractice.member.service.MemberService;

@RunWith(SpringRunner.class)
@SpringBootTest
class MemberServiceTest {

	@Autowired
	MemberService memberService;
	
	@Test
	void test() {
	}

}
