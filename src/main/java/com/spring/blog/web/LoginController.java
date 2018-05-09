package com.spring.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
	@GetMapping(path = "loginForm")
    String loginForm() {
        return "loginForm";
    }
    
    @PostMapping(path = "loginForm", params = "goToTop")
    String loginForm2list() {
    		return "redirect:/articles";
    }
	
}