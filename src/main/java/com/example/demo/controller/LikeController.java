package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.LikeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/like")
    public String likeUser(@RequestBody LikeRequest likeRequest, Authentication authentication) {
        String currentUserId = authentication.getName();
        likeService.likeUser(likeRequest.getLoginId(), currentUserId);
        return "{\"success\":true}";
    }

    @PostMapping("/unlike")
    public String unlikeUser(@RequestBody LikeRequest likeRequest, Authentication authentication) {
        String currentUserId = authentication.getName();
        likeService.unlikeUser(likeRequest.getLoginId(), currentUserId);
        return "{\"success\":true}";
    }
}
