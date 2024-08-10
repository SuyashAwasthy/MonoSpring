package com.techlabs.hibernate.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.hibernate.dto.CommentDTO;
import com.techlabs.hibernate.service.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Validated
public class CommentController {
	 private final CommentService commentService = new CommentService();

	    @GetMapping
	    public ResponseEntity<List<CommentDTO>> getAllComments() {
	        return ResponseEntity.ok(commentService.getAllComments());
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
	        return ResponseEntity.ok(commentService.getCommentById(id));
	    }

	    @PostMapping
	    public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody CommentDTO commentDTO) {
	        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(commentDTO));
	    }
	    
	    @PutMapping("/{id}")
	    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @Valid @RequestBody CommentDTO commentDTO) {
	        return ResponseEntity.ok(commentService.updateComment(id, commentDTO));
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
	        commentService.deleteComment(id);
	        return ResponseEntity.noContent().build();
	    }
}
