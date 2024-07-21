package com.example.demo.controller;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.LikeService;
import com.example.demo.service.UserIntroductionService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserIntroductionController {

    private final UserIntroductionService userIntroductionService;
    private final LikeService likeService;

    @GetMapping("/userintroduction")
    public String showUserIntroductionList(Model model, Authentication authentication) {
        List<UserInfo> users = userIntroductionService.getUserList();
        String currentUserId = authentication.getName();

        List<UserIntroductionView> userViews = users.stream()
            .map(user -> {
                boolean liked = likeService.hasLiked(user.getLoginId(), currentUserId);
                String base64Image = user.getProfImg() != null ? Base64.getEncoder().encodeToString(user.getProfImg()) : "";
                return new UserIntroductionView(user, base64Image, liked);
            })
            .collect(Collectors.toList());

        model.addAttribute("users", userViews);
        return "userIntroductionList";
    }

    private static class UserIntroductionView {
        private final UserInfo user;
        private final String base64Image;
        private final boolean liked;

        public UserIntroductionView(UserInfo user, String base64Image, boolean liked) {
            this.user = user;
            this.base64Image = base64Image;
            this.liked = liked;
        }

        public String getLoginId() {
            return user.getLoginId();
        }

        public String getUserName() {
            return user.getUser_name();
        }

        public long getLikeCount() {
            return user.getLikeCount();
        }

        public String getBase64Image() {
            return base64Image;
        }

        public boolean isLiked() {
            return liked;
        }
    }
}
