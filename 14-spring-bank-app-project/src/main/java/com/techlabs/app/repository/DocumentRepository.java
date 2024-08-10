package com.techlabs.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.app.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long>{

}
