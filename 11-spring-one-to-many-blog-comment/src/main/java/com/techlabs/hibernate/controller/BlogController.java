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

import com.techlabs.hibernate.dto.BlogDTO;
import com.techlabs.hibernate.service.BlogService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
@Validated
public class BlogController {

	private final BlogService blogService = new BlogService();

    @GetMapping
    public ResponseEntity<List<BlogDTO>> getAllBlogs() {
        return ResponseEntity.ok(blogService.getAllBlogs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogDTO> getBlogById(@PathVariable Long id) {
        return ResponseEntity.ok(blogService.getBlogById(id));
    }

    @PostMapping
    public ResponseEntity<BlogDTO> createBlog(@Valid @RequestBody BlogDTO blogDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(blogService.createBlog(blogDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogDTO> updateBlog(@PathVariable Long id, @Valid @RequestBody BlogDTO blogDTO) {
        return ResponseEntity.ok(blogService.updateBlog(id, blogDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build();
    }
	
}
