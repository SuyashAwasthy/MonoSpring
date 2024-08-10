package com.techlabs.hibernate.service;

import com.techlabs.hibernate.entity.FileItem;

import java.util.Optional;

public interface FileService {

	 FileItem saveFile(FileItem file);

	 Optional<FileItem> getFileByName(String fileName);
	
}
