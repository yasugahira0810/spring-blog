package com.spring.blog.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "articles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private LocalDateTime postDate;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String bodyText;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = true, name = "username")
	private User user;
}
