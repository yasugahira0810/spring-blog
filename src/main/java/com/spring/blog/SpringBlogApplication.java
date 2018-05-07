package com.spring.blog;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.spring.blog.domain.Article;
import com.spring.blog.repository.ArticleRepository;

@SpringBootApplication
public class SpringBlogApplication implements CommandLineRunner {
	@Autowired
	ArticleRepository articleRepository;
	
	@Override
	public void run(String...strings) throws Exception {
		Article created = articleRepository.save(new Article(null, LocalDateTime.now(), "ぬるぬるについて考えた", "ぬるぬる"));
		System.out.println(created + " is created!");
		articleRepository.findAllOrderByPostDate().forEach(System.out::println);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBlogApplication.class, args);
	}

}
