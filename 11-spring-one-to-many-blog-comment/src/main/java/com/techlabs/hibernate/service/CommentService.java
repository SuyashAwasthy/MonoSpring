package com.techlabs.hibernate.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.techlabs.hibernate.dto.CommentDTO;
import com.techlabs.hibernate.entity.Comment;
import com.techlabs.hibernate.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    public List<CommentDTO> getAllComments() {
        return commentRepository.findAll().stream()
                .map(comment -> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());
    }

    public CommentDTO getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + id));
        return modelMapper.map(comment, CommentDTO.class);
    }

    public CommentDTO createComment(CommentDTO commentDTO) {
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        comment = commentRepository.save(comment);
        return modelMapper.map(comment, CommentDTO.class);
    }

    public CommentDTO updateComment(Long id, CommentDTO commentDTO) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + id));
        modelMapper.map(commentDTO, existingComment);
        existingComment = commentRepository.save(existingComment);
        return modelMapper.map(existingComment, CommentDTO.class);
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + id));
        commentRepository.delete(comment);
    }
	
}
