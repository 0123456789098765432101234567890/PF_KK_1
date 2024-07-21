package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.LikeService;
import com.example.demo.service.UserInfoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserIntroductionController {

    private final UserInfoService userInfoService;
    private final LikeService likeService;

    @GetMapping("/userintroduction")
    public String getUserIntroduction(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<UserInfo> users = userInfoService.getAllUsersWithRoleUser();
        String loginId = userDetails.getUsername();

        for (UserInfo user : users) {
            boolean liked = likeService.hasLiked(user.getLoginId(), loginId);
            user.setLiked(liked);
        }

        model.addAttribute("users", users);
        return "userIntroductionList";
    }
}
