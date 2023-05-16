package com.spring.bootPractice.global.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.spring.bootPractice.product.dto.ImageRequestDto;

@Component
public class FileUpload {
	
	@Value("${file.dir}")
	private String path;
	
	public List<ImageRequestDto> parseFileInfo(List<MultipartFile> files) {
		
		List<ImageRequestDto> dto = new ArrayList<>();
		
		File target = new File(path); //파일 경로
		if (!target.exists()) //만약 해당 경로의 폴더가 없으면 생성
			target.mkdirs();
		
		Iterator<MultipartFile> iterator = files.iterator();
		while(iterator.hasNext()) {
			MultipartFile file = iterator.next();
			String org_name = file.getOriginalFilename();
			String extension = org_name.substring(org_name.lastIndexOf("."));
			String save_name = UUID.randomUUID().toString().replaceAll("-", "");
			
			target = new File(path, save_name + extension);
			
			try {
				file.transferTo(target);
				
				dto.add(ImageRequestDto.builder()
						.org_name(org_name)
						.save_name(save_name)
						.extension(extension)
						.path(path)
						.build()
				);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			
		}
		
		return dto;
	}
	
}
