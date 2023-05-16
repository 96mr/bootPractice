package com.spring.bootPractice.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.bootPractice.product.entity.Category;
import com.spring.bootPractice.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
	public Optional<Product> findByPid(int pid);
	public List<Product> findByPcategory(Category pcategory);
}
