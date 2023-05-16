package com.spring.bootPractice.product.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
import com.spring.bootPractice.global.common.FileUpload;
import com.spring.bootPractice.product.dto.ImageRequestDto;
import com.spring.bootPractice.product.dto.ProductRequestDto;
import com.spring.bootPractice.product.dto.ProductResponseDto;
import com.spring.bootPractice.product.entity.Category;
import com.spring.bootPractice.product.entity.ImageCategory;
import com.spring.bootPractice.product.entity.Product;
import com.spring.bootPractice.product.repository.CategoryRepository;
import com.spring.bootPractice.product.repository.ImageRepository;
import com.spring.bootPractice.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {
	
	@Value("${file.infoDir}")
	private String path;
	
	private final ProductRepository productRepository;
	private final ImageRepository imageRepository;
	private final CategoryRepository categoryRepository;
	private final FileUpload fileUpload;
	
	@Transactional
	public String save(ProductRequestDto productDto, List<MultipartFile> files) {
		Product product = productRepository.save(productDto.toEntity());
		List<ImageRequestDto> list = fileUpload.parseFileInfo(files);
		Iterator<ImageRequestDto> iterator = list.iterator();
		while(iterator.hasNext()) {
			ImageRequestDto image = iterator.next();
			image.setPid(product);
			image.setCategory(ImageCategory.PRODUCT);
			imageRepository.save(image.toEntity());
		}
		return product.getPcategory().getName();
	}
	
	
	public List<ProductResponseDto> read(String category) {
		List<Product> product = new ArrayList<>();
		if(category != null) {
			Category pcategory = categoryRepository.findByName(category)
					.orElseThrow(()-> new IllegalStateException("존재하지 않는 카테고리입니다."));
			product = productRepository.findByPcategory(pcategory);
		}else {		
			product = productRepository.findAll();
		}
		List<ProductResponseDto> dtoList = new ArrayList<>();
		for(Product p : product) {
			dtoList.add(new ProductResponseDto(p));
		}
		
		return dtoList;
	}
	
	public ProductResponseDto getProductInfo(int pid) {
		Product product= productRepository.findByPid(pid)
										.orElseThrow(()-> new IllegalStateException("존재하지 않는 상품입니다."));
		return new ProductResponseDto(product);
	}
	
	public JsonObject uploadImage(MultipartFile multipartFile) {
		JsonObject json = new JsonObject();
	
		String orgName = multipartFile.getOriginalFilename();
		String extension = orgName.substring(orgName.lastIndexOf("."));
		
		String savedFileName = UUID.randomUUID().toString().replaceAll("-", "") + extension;
		
		File target = new File(path);
		if(!target.exists()) {
			target.mkdirs();
		}
		
		target = new File(path, savedFileName); // filePath에 savedFileName 업로드
		
		try {
			InputStream fileStream = multipartFile.getInputStream();
			FileUtils.copyInputStreamToFile(fileStream, target);
			json.addProperty("url","/bootPractice/productInfo/"+ savedFileName);
			json.addProperty("responseCode", "success");
		}catch(IOException e) {
			FileUtils.deleteQuietly(target);
			json.addProperty("responseCode", "error");
			e.printStackTrace();
		}
				
		return json;
	}
	
}
