package com.spring.blog.web;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ArticleForm {
	@NotNull
	@Size(min = 1, max = 50)
	private String title;
	@NotNull
	@Size(min = 1, max = 2000)
	private String bodyText;
	private LocalDateTime postDate;
	
	public LocalDateTime getPostDate() {
		return LocalDateTime.now();
	}
}
