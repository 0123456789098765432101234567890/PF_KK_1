package com.example.demo.controller;

import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.constant.UrlConst;
import com.example.demo.form.LoginForm;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final HttpSession session;

    @GetMapping(UrlConst.LOGIN)
    public String view(Model model, LoginForm form) {
        return "login"; // "login"テンプレート名を直接指定
    }

    @GetMapping(value = UrlConst.LOGIN, params = "error")
    public String viewWithError(Model model, LoginForm form) {
        var errorInfo = (Exception) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        String errorMessage = errorInfo != null ? errorInfo.getMessage() : "ログインIDかパスワードが違います。";

        model.addAttribute("errorMsg", errorMessage);
        return "login"; // "login"テンプレート名を直接指定
    }
}
