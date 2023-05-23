package com.spring.bootPractice.product.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring.bootPractice.product.entity.Category;
import com.spring.bootPractice.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
	public Optional<Product> findByPid(int pid);
	public Page<Product> findAll(Pageable pageable);
	public Page<Product> findByPcategory(Category pcategory, Pageable pageable);
	@Query("SELECT p FROM Product p WHERE p.pcategory.parent= :parent")
	public Page<Product> categoryByParent(@Param("parent") Category parent, Pageable pageable);
}
