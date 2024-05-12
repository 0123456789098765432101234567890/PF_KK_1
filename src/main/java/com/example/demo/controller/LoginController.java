package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.LoginForm;
import com.example.demo.service.LoginService;

import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class LoginController {
	
	private final LoginService service;
	
	@GetMapping("/login")
	public String view(Model model,LoginForm form) {
		
		return"login";
	}
	
	@PostMapping("/login")
	public String login(Model model,LoginForm form) {
		var userInfo = service.searchUserById(form.getLoginId());
		var isCorrectUserAuth = userInfo.isPresent()
				&& form.getPassword().equals(userInfo.get().getPassword());
		if(isCorrectUserAuth) {
			return "redirect:/menu";
		}else {
			model.addAttribute("ermsg","eroor");
			return "login";
		}
	}

}
