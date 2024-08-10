package com.techlabs.hibernate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BlogResponseDTO {
    private int id;

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 2,max = 50, message = "Title should have 2 to 50 characters")
    private String title;

    @NotBlank
    private String category;

    @NotBlank
    private String data;

    private LocalDateTime publishedDate;

    private List<CommentDTO> commentDTOList;

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public LocalDateTime getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(LocalDateTime publishedDate) {
		this.publishedDate = publishedDate;
	}

	public List<CommentDTO> getCommentDTOList() {
		return commentDTOList;
	}

	public void setCommentDTOList(List<CommentDTO> commentDTOList) {
		this.commentDTOList = commentDTOList;
	}

	@Override
    public String toString() {
        return "BlogResponseDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", data='" + data + '\'' +
                ", publishedDate=" + publishedDate +
                ", commentDTOList=" + commentDTOList +
                '}';
    }
}