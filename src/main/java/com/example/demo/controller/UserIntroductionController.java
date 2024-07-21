package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserInfoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserIntroductionController {

    private final UserInfoService userInfoService;

    @GetMapping("/userintroduction")
    public String getUserIntroduction(Model model) {
        List<UserInfo> users = userInfoService.getAllUsersWithRoleUser();
        model.addAttribute("users", users);
        return "userIntroductionList";  // テンプレート名が "userIntroductionList" に一致
    }
}
