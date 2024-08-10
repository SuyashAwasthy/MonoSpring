package com.techlabs.hibernate.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogDTO {

	private Long id;
    private String title;
    private String category;
    private String data;
    private LocalDateTime publishedDate;
    private Boolean published;
    private List<CommentDTO> comments;
	
}
