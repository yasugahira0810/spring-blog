package com.spring.blog.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import com.spring.blog.domain.Article;

@Repository
public class ArticleRepository {
	private final ConcurrentMap<Integer, Article> articleMap = new ConcurrentHashMap<>();
	
	public List<Article> findAll() {
		return new ArrayList<>(articleMap.values());
	}
	
	public Article findOne(Integer ArticleId) {
		return articleMap.get(ArticleId);
	}
	
	public Article save(Article article) {
		return articleMap.put(article.getId(), article);
	}
	
	public void delete(Integer articleId) {
		articleMap.remove(articleId);
	}
}
