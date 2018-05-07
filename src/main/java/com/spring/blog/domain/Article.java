package com.spring.blog.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Article {
	private Integer id;
	private LocalDateTime postDate;
	private String title;
	private String bodyText;
}
