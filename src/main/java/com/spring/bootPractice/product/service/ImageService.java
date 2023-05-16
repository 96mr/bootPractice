package com.spring.bootPractice.product.service;

import org.springframework.stereotype.Service;

import com.spring.bootPractice.product.repository.ImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {
	private final ImageRepository imageRepository;
}
