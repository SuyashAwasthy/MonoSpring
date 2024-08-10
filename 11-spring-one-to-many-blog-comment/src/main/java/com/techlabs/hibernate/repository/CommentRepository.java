package com.techlabs.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.techlabs.hibernate.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

}
