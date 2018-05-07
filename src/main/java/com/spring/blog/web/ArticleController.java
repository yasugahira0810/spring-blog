package com.spring.blog.web;

import com.spring.blog.domain.Article;
import com.spring.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("articles")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @GetMapping
    String list(Model model) {
        List<Article> articles = articleService.findAll();
        model.addAttribute("articles", articles);
        return "articles/list";
    }

//    @GetMapping(path = "{id}")
//    Article getArticle(@PathVariable Integer id) {
//        Article Article = articleService.findOne(id);
//        return Article;
//    }
//
//    @PostMapping
//    ResponseEntity<Article> postArticles(@RequestBody Article Article, UriComponentsBuilder uriBuilder) {
//        Article created = articleService.create(Article);
//        URI location = uriBuilder.path("api/Articles/{id}")
//                .buildAndExpand(created.getId()).toUri();
//        return ResponseEntity.created(location).body(created);
//    }
//
//    @PutMapping(path = "{id}")
//    Article putArticle(@PathVariable Integer id, @RequestBody Article Article) {
//        Article.setId(id);
//        return articleService.update(Article);
//    }
//
//    @DeleteMapping(path = "{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    void deleteArticle(@PathVariable Integer id) {
//        articleService.delete(id);
//    }
}