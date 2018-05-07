package com.spring.blog;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.spring.blog.domain.Article;
import com.spring.blog.service.ArticleService;

@SpringBootApplication
public class SpringBlogApplication implements CommandLineRunner {
	@Autowired
	ArticleService ArticleService;
	
	@Override
	public void run(String...strings) throws Exception {
		ArticleService.save(new Article(1, LocalDateTime.now(),"ほげほげ試してみた", "ほげほげ"));
		ArticleService.save(new Article(2, LocalDateTime.now(),"ふがふが参加レポート", "ふがふが"));
		ArticleService.save(new Article(3, LocalDateTime.now(),"バーバーツール", "バーバー"));
		
		ArticleService.findAll().forEach(System.out::println);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBlogApplication.class, args);
	}

}
