package com.techlabs.hibernate.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.techlabs.hibernate.entity.ImageData;
import com.techlabs.hibernate.repository.StorageRepository;
import com.techlabs.hibernate.util.ImageUtils;

@Service
public class StorageService {

	@Autowired
	private StorageRepository repository;
	
	public String uploadImage(MultipartFile file) throws IOException{
		ImageData imageData = ImageData.builder()
				.name(file.getOriginalFilename())
				.type(file.getContentType())
				.imageData(ImageUtils.compressImage(file.getBytes()))
				.build();
	
		imageData = repository.save(imageData);
		
		if(imageData != null) {
			return "file uploaded successfully: "+file.getOriginalFilename();
		}
		return null;
	}
	
	public byte[] downloadImage(String fileName) {
		Optional<ImageData> dbImageData = repository.findByName(fileName);
		if(dbImageData.isPresent()) {
			return ImageUtils.decompressImage(dbImageData.get().getImageData());			
		}
		else {
			throw new RuntimeException("Image not found with name: " + fileName);
		}
	}
	
}
