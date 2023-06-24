package com.spring.bootPractice.member.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.bootPractice.member.entity.Member;
import com.spring.bootPractice.member.entity.MemberDetail;
import com.spring.bootPractice.member.repository.MemberRepository;

@Service
public class MemberDetailService implements UserDetailsService{

	private final MemberRepository memberRepository;

	public MemberDetailService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		Member member = memberRepository.findById(id)
							.orElseThrow(()-> new UsernameNotFoundException(id));
		return new MemberDetail(member);
	}
	
}
