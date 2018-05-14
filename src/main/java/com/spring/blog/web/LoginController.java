package com.spring.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	@GetMapping(path = "/")
	String top() {
		return "redirect:/articles";
	}
	@GetMapping(path = "loginForm")
    String loginForm() {
        return "loginForm";
    }
    
    @PostMapping(path = "loginForm", params = "goToTop")
    String loginForm2list() {
    		return "redirect:/articles";
    }
	
}