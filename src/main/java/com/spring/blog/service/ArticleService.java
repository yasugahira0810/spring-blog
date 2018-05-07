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
	
	public List<Article> findAll() {
		return articleRepository.findAllOrderByPostDate();
	}
	
	public Article findOne(Integer id) {
		return articleRepository.findById(id).orElse(null);
	}
	
	public Article create(Article article) {
		return articleRepository.save(article);
	}
	
	public Article update(Article article) {
		return articleRepository.save(article);
	}
	
	public void delete(Integer id) {
		articleRepository.deleteById(id);
	}
}
