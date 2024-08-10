package com.techlabs.hibernate.controller;

import com.techlabs.hibernate.dto.BlogRequestDTO;
import com.techlabs.hibernate.dto.BlogResponseDTO;
import com.techlabs.hibernate.service.BlogService;
import com.techlabs.hibernate.util.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    private final BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public ResponseEntity<PagedResponse<BlogResponseDTO>> getAllBlogs(
            @RequestParam(name = "pageNumber",defaultValue = "0")int pageNumber,
            @RequestParam(name = "size", defaultValue = "3")int size,
            @RequestParam(name = "sortBy", defaultValue = "id")String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc")String direction
    ) {
        PagedResponse<BlogResponseDTO> pagedResponseDTOS = blogService
                .getAllBlogs(pageNumber,size,sortBy,direction);
        return new ResponseEntity<>(pagedResponseDTOS, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<BlogResponseDTO> getBlogById(@Valid @PathVariable(name = "id") int id) {
        BlogResponseDTO blogResponseDTO = blogService.getBlogById(id);
        return new ResponseEntity<>(blogResponseDTO, HttpStatus.FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<BlogResponseDTO> createNewBlog(@Valid @RequestBody BlogRequestDTO blogRequestDTO) {
        BlogResponseDTO blogResponseDTO = blogService.createNewBlog(blogRequestDTO);
        return new ResponseEntity<>(blogResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<BlogResponseDTO> updateBlog(@Valid @RequestBody BlogRequestDTO blogRequestDTO) {
        BlogResponseDTO blogResponseDTO = blogService.updateBlog(blogRequestDTO);
        return new ResponseEntity<>(blogResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<Object> deleteBlog(@Valid @PathVariable(name = "id") int id) {
        blogService.deleteBlog(id);
        return ResponseEntity.status(HttpStatus.OK).body("Blog with ID : " + id + " Deleted Successfully");
    }

    @DeleteMapping("/delete/cid/{id}")
    public ResponseEntity<Object> deleteBlogByCommentId(@Valid @PathVariable(name = "id") int id) {
        blogService.deleteBlogByCommentId(id);
        return ResponseEntity.status(HttpStatus.OK).body("Blog related to comment with ID : " + id + " deleted successfully");
    }

}