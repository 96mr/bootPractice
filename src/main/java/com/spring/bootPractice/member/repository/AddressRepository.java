package com.spring.bootPractice.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.bootPractice.member.entity.Address;
import com.spring.bootPractice.member.entity.Member;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
	public Optional<Address> findByMemberId(Member member);
}
