package com.example.demo.controller;

import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.constant.MessageConst;
import com.example.demo.constant.UrlConst;
import com.example.demo.constant.ViewNameConst;
import com.example.demo.form.LoginForm;
import com.example.demo.service.LoginService;
import com.example.demo.util.AppUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class LoginController {
	
	/** セッション情報 */
	private final HttpSession session;
	
	/* ログイン画面 service */
	private final LoginService service;
	
	/* passwordEncoder */
	private final PasswordEncoder passwordEncoder;
	
	/* メッセージソース */
	private final MessageSource messageSource;
	
	@GetMapping({"/",UrlConst.LOGIN})
	public String view(Model model,LoginForm form) {
		
		return UrlConst.LOGIN;
	}
	
	/**
	 * ログインエラー時にセッションからエラーメッセージを取得して、画面の表示を行います。
	 * 
	 * @param model モデル
	 * @param form 入力情報
	 * @return ログイン画面テンプレート名
	 */
	@GetMapping(value = UrlConst.LOGIN, params = "error")
	public String viewWithError(Model model, LoginForm form) {
		var errorInfo = (Exception) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		model.addAttribute("errorMsg",errorInfo.getMessage());
/*		model.addAttribute(ModelKey.MESSAGE, errorInfo.getMessage());
		model.addAttribute(ModelKey.IS_ERROR, true); */

		return ViewNameConst.LOGIN;
	}
	
	
	@PostMapping(UrlConst.LOGIN)
	public String login(Model model,LoginForm form) {
		var userInfo = service.searchUserById(form.getLoginId());
		var encodedPassword = passwordEncoder.encode(form.getPass());
		
		var isCorrectUserAuth = userInfo.isPresent()
				
				&& passwordEncoder.matches(form.getPass() , 
						userInfo.get().getPass()) ;
		
		if(isCorrectUserAuth) {
			return "redirect:/menu";
		}else {
			var errorMsg = AppUtil.getMessage(messageSource, MessageConst.LOGIN_WRONG_INPUT);
			model.addAttribute("errorMsg",errorMsg);
			return UrlConst.LOGIN;
		}
	}

}
