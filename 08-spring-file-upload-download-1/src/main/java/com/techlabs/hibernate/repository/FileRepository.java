package com.techlabs.hibernate.repository;
import com.techlabs.hibernate.entity.FileItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileItem, Integer>{
	Optional<FileItem> findByName(String fileName);
}
