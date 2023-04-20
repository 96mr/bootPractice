package com.spring.bootPractice.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.bootPractice.entity.Member;
import com.spring.bootPractice.entity.MemberDetail;
import com.spring.bootPractice.repository.MemberRepository;

@Service
public class MemberDetailService implements UserDetailsService{

	private final MemberRepository memberRepository;
	private PasswordEncoder passwordEncoder;
	
	public MemberDetailService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		Member member = memberRepository.findById(id)
							.orElseThrow(()-> new UsernameNotFoundException(id));
		return new MemberDetail(member);
	}

	public Member save(Member member) {
		if(checkId(member.getId())) {
			return new Member();
		}
		
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		return memberRepository.save(member);
	}

	public Boolean checkId(String id) {
		return memberRepository.findById(id).isPresent();
	}
	
}
