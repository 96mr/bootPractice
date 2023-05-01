package com.spring.bootPractice.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.spring.bootPractice.member.repository.MemberRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class MemberRepositoryTest {

	@Autowired
	MemberRepository repository;
	
	@Test
	void test() {
		System.out.println(repository.findById("example"));
	}

}
