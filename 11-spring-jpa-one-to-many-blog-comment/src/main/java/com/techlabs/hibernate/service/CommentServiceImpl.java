package com.techlabs.hibernate.service;

import com.techlabs.hibernate.dto.CommentDTO;
import com.techlabs.hibernate.entity.Blog;
import com.techlabs.hibernate.entity.Comment;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl implements CommentService {

    @Override
    public Comment commentDTOToComment(CommentDTO commentDTO, Blog blog) {
        if (commentDTO == null || blog == null) {
            return null;
        }

        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setDescription(commentDTO.getDescription());
        comment.setBlog(blog);

        return comment;
    }

    @Override
    public CommentDTO commentToCommentDTO(Comment comment) {
        if (comment == null) {
            return null;
        }
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setDescription(comment.getDescription());

        return commentDTO;
    }


}