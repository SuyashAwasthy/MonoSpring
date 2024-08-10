package com.techlabs.hibernate.service;

import com.techlabs.hibernate.dto.BlogRequestDTO;
import com.techlabs.hibernate.dto.BlogResponseDTO;
import com.techlabs.hibernate.dto.CommentDTO;
import com.techlabs.hibernate.entity.Blog;
import com.techlabs.hibernate.util.PagedResponse;

import jakarta.validation.Valid;

public interface BlogService {

	PagedResponse<BlogResponseDTO> getAllBlogs(int pageNumber, int size, String sortBy, String direction);

	BlogResponseDTO getBlogById(int id);

	BlogResponseDTO createNewBlog(BlogRequestDTO blogRequestDTO);

	BlogResponseDTO updateBlog(BlogRequestDTO blogRequestDTO);

	void deleteBlog(int id);

	Blog blogRequestDTOtoBlog(BlogRequestDTO blogRequestDTO);

	BlogResponseDTO blogToBlogResponse(Blog blog);

	BlogResponseDTO createNewComment(CommentDTO commentDTO, int id);

	BlogResponseDTO updateComment(CommentDTO commentDTO, int id);

	void deleteBlogByCommentId(int id);

	void deleteComment(int cid, int id);

}