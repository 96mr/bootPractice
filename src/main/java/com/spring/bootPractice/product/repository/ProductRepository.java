package com.spring.bootPractice.product.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.bootPractice.member.entity.Member;
import com.spring.bootPractice.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Member,Long> {
	public Optional<Product> findById(int pid);
}
