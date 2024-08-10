package com.techlabs.hibernate.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.techlabs.hibernate.entity.FileItem;
import com.techlabs.hibernate.repository.FileRepository;

@Service
public class FileServiceImpl implements FileService{
  
	private FileRepository repository;

    public FileServiceImpl(FileRepository repository) {
        this.repository = repository;
    }
	
	@Override
	public FileItem saveFile(FileItem file) {
        return repository.save(file);
	}

	@Override
	public Optional<FileItem> getFileByName(String fileName) {
		Optional<FileItem> item = repository.findByName(fileName);
        return item;
	}

}
