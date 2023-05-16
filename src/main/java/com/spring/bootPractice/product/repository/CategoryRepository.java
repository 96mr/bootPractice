package com.spring.bootPractice.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.bootPractice.product.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
	public Optional<Category> findById(int id);
	public Optional<Category> findByName(String name);
	public List<Category> findByParentIsNullOrderByIdAsc();
	public List<Category> findByParentNotNullOrderByIdAsc();
}
