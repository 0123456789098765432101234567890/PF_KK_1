package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.LikeService;
import com.example.demo.service.UserInfoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
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

    @GetMapping("/userdetail")
    public String getUserDetail(@RequestParam("loginId") String loginId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        log.debug("Fetching details for user with loginId: {}", loginId);
        UserInfo user = userInfoService.findByLoginId(loginId);
        if (user == null) {
            log.debug("User not found, redirecting to user introduction list");
            return "redirect:/userintroduction";
        }	
        boolean liked = likeService.hasLiked(loginId, userDetails.getUsername());
        user.setLiked(liked);
        model.addAttribute("user", user);
        return "userdetailintro";  // テンプレート名が "userdetailintro" に一致
    }
}
