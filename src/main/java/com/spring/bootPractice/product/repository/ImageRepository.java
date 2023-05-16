package com.spring.bootPractice.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.bootPractice.product.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
}
