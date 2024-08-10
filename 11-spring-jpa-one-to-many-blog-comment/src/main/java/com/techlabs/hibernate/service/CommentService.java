package com.techlabs.hibernate.service;

import com.techlabs.hibernate.dto.CommentDTO;
import com.techlabs.hibernate.entity.Blog;
import com.techlabs.hibernate.entity.Comment;

public interface CommentService {

	Comment commentDTOToComment(CommentDTO commentDTO, Blog blog);
    CommentDTO commentToCommentDTO(Comment comment);
	
}
