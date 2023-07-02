package com.spring.bootPractice.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.bootPractice.member.entity.Member;
import com.spring.bootPractice.order.entity.Cart;
import com.spring.bootPractice.product.entity.Product;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {
	public Optional<Cart> findById(int id);
	public List<Cart> findByMemberId(Member memberId);
	@Modifying
    @Transactional
	@Query(value ="DELETE FROM Cart c WHERE c.memberId.id = :memberId AND c.productId = :productId")
	public void deleteByMemberIdAndProductId(@Param("memberId") String memberId, @Param("productId") Product productId);
}
