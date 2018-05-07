package com.spring.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.blog.domain.Article;
import com.spring.blog.repository.ArticleRepository;

@Service
public class ArticleService {
	@Autowired
	ArticleRepository articleRepository;
	
	public Article save(Article article) {
		return articleRepository.save(article);
	}
	
	public List<Article> findAll() {
		return articleRepository.findAll();
	}
}
