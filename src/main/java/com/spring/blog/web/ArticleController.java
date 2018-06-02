package com.spring.blog.web;

import com.spring.blog.domain.Article;
import com.spring.blog.service.ArticleService;
import com.spring.blog.service.LoginUserDetails;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("articles")
public class ArticleController {
    @Autowired
    ArticleService articleService;
    
    @ModelAttribute
    ArticleForm setUpForm() {
    		return new ArticleForm();
    }

    @GetMapping
    String list(Model model) {
        List<Article> articles = articleService.findAll();
        Collections.reverse(articles); // 最新の記事をブログ上部に表示するためにリストを反転
        model.addAttribute("articles", articles);
        return "articles/list";
    }
    
    @GetMapping(path = "create")
    String newArticle() {
    		return "articles/create";
    }
    
    @PostMapping(path = "create")
    String create(@Validated ArticleForm form, BindingResult result, Model model, @AuthenticationPrincipal LoginUserDetails userDetails) {
    		if (result.hasErrors()) {
    			return newArticle();
    		}
    		Article article = new Article();
    		BeanUtils.copyProperties(form, article);
    		article.setPostDate(LocalDateTime.now());
    		articleService.create(article, userDetails.getUser());
    		return "redirect:/articles";
    }
    
    @PostMapping(path = "create", params = "goToTop")
    String create2list() {
    		return "redirect:/articles";
    }
    
    @GetMapping(path = "show")
    String show(@RequestParam Integer id, Model model) {
    		Article article = articleService.findOne(id);
        model.addAttribute("article", article);
        return "articles/show";
    }
    
    @GetMapping(path = "show", params = "goToTop")
    String show2list() {
    		return "redirect:/articles";
    }
    
    @GetMapping(path = "edit", params = "form")
    String editForm(@RequestParam Integer id, ArticleForm form) {
    		Article article = articleService.findOne(id);
    		BeanUtils.copyProperties(article, form);
    		return "articles/edit";
    }
    
    @PostMapping(path = "edit")
    String edit(@RequestParam Integer id, @Validated ArticleForm form, BindingResult result) {
    if (result.hasErrors()) {
    		return editForm(id, form);
    }
    Article article = articleService.findOne(id);
    BeanUtils.copyProperties(form, article);
    articleService.update(article);
    	return "redirect:/articles";
    }
        
    @PostMapping(path = "edit", params = "goToTop")
    String edit2list() {
    		return "redirect:/articles";
    }
    
    @GetMapping(path = "delete")
    String delete(@RequestParam Integer id) {
    		articleService.delete(id);
    		return "redirect:/articles";
    }

}