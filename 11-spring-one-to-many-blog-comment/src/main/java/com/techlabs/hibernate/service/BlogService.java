package com.techlabs.hibernate.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.techlabs.hibernate.dto.BlogDTO;
import com.techlabs.hibernate.entity.Blog;
import com.techlabs.hibernate.repository.BlogRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogService {

	private final BlogRepository blogRepository;
    private final ModelMapper modelMapper;

    public List<BlogDTO> getAllBlogs() {
        return blogRepository.findAll().stream()
                .map(blog -> modelMapper.map(blog, BlogDTO.class))
                .collect(Collectors.toList());
    }

    public BlogDTO getBlogById(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id " + id));
        return modelMapper.map(blog, BlogDTO.class);
    }

    public BlogDTO createBlog(BlogDTO blogDTO) {
        Blog blog = modelMapper.map(blogDTO, Blog.class);
        blog = blogRepository.save(blog);
        return modelMapper.map(blog, BlogDTO.class);
    }

    public BlogDTO updateBlog(Long id, BlogDTO blogDTO) {
        Blog existingBlog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id " + id));
        modelMapper.map(blogDTO, existingBlog);
        existingBlog = blogRepository.save(existingBlog);
        return modelMapper.map(existingBlog, BlogDTO.class);
    }

    public void deleteBlog(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id " + id));
        blogRepository.delete(blog);
    }
	
}
