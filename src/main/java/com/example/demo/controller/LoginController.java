package com.example.demo.controller;

import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.LoginForm;
import com.example.demo.service.LoginService;
import com.example.demo.util.AppUtil;

import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class LoginController {
	
	/* ログイン画面 service */
	private final LoginService service;
	
	/* passwordEncoder */
	private final PasswordEncoder passwordEncoder;
	
	/* メッセージソース */
	private final MessageSource messageSource;
	
	@GetMapping({"/","/login"})
	public String view(Model model,LoginForm form) {
		
		return"login";
	}
	
	
	@PostMapping("/login")
	public String login(Model model,LoginForm form) {
		var userInfo = service.searchUserById(form.getLoginId());
		var encodedPassword = passwordEncoder.encode(form.getPass());
		
		var isCorrectUserAuth = userInfo.isPresent()
				
				&& passwordEncoder.matches(form.getPass() , 
						userInfo.get().getPass()) ;
		
		if(isCorrectUserAuth) {
			return "redirect:/menu";
		}else {
			var errorMsg = AppUtil.getMessage(messageSource, "login.wrongInput");
			model.addAttribute("ermsg",errorMsg);
			return "login";
		}
	}

}
