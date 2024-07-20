package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.LikeService;
import com.example.demo.service.UserInfoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/userintroduction")
public class UserIntroductionController {
    private final UserInfoService userInfoService;
    private final LikeService likeService;

    @GetMapping
    public String showUserIntroductionList(Model model) {
        List<UserInfo> users = userInfoService.getAllUsersWithRoleUser();
        model.addAttribute("users", users);
        return "userIntroductionList";
    }

    @PostMapping("/like")
    @ResponseBody
    public String likeUser(@RequestParam("loginId") String loginId, Authentication authentication) {
        String fromLoginId = authentication.getName();
        likeService.likeUser(loginId, fromLoginId);
        return "success";
    }

    @PostMapping("/unlike")
    @ResponseBody
    public String unlikeUser(@RequestParam("loginId") String loginId, Authentication authentication) {
        String fromLoginId = authentication.getName();
        likeService.unlikeUser(loginId, fromLoginId);
        return "success";
    }

    @GetMapping("/like/count")
    @ResponseBody
    public Long getLikeCount(@RequestParam("loginId") String loginId) {
        return likeService.getLikeCount(loginId);
    }
}
