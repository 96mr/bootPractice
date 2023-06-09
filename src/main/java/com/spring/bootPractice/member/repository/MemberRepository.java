package com.spring.bootPractice.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.bootPractice.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
	public Optional<Member> findById(String id);
	public Optional<Member> findByEmail(String email);
}
