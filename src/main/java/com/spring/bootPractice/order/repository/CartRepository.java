package com.spring.bootPractice.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.bootPractice.member.entity.Member;
import com.spring.bootPractice.order.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {
	public Optional<Cart> findById(int id);
	public List<Cart> findByMemberId(Member memberId);
}
