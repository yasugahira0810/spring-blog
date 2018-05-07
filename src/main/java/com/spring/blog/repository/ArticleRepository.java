package com.spring.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.blog.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
	@Query("SELECT x FROM Article x ORDER BY x.postDate")
	List<Article> findAllOrderByPostDate();
}
