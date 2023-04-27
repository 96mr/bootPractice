package com.spring.bootPractice.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.bootPractice.entity.Member;
import com.spring.bootPractice.entity.MemberDTO;
import com.spring.bootPractice.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {
	
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Transactional
	public Member save(MemberDTO memberDto) {
		duplicateId(memberDto.getId());
		duplicateEmail(memberDto.getEmail());
	
		memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
		Member member = memberDto.toEntity();
		return memberRepository.save(member);
	}

	public void duplicateId(String id) {
		memberRepository.findById(id)
						.ifPresent(m-> {
							throw new IllegalStateException("이미 가입된 회원입니다.");
						});
	}
	
	public void duplicateEmail(String email) {
		memberRepository.findByEmail(email)
						.ifPresent(m-> {
							throw new IllegalStateException("이미 가입된 이메일입니다.");
						});
	}
}
