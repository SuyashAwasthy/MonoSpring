package com.techlabs.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techlabs.hibernate.entity.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long>{

}
