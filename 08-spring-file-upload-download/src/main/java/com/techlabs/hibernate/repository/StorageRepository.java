package com.techlabs.hibernate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.hibernate.entity.ImageData;

public interface StorageRepository extends JpaRepository<ImageData,Long>{

	Optional<ImageData> findByName(String fileName);
}
