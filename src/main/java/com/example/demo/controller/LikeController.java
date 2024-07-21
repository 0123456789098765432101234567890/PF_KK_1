package com.example.demo.controller;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.LikeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/like")
    @ResponseBody
    public void likeUser(@RequestBody Map<String, String> request, @AuthenticationPrincipal UserDetails userDetails) {
        String loginId = request.get("loginId");
        String fromLoginId = userDetails.getUsername();
        likeService.likeUser(loginId, fromLoginId);
        System.out.println("Like request processed for user: " + loginId + " by: " + fromLoginId);
    }

    @PostMapping("/unlike")
    @ResponseBody
    public void unlikeUser(@RequestBody Map<String, String> request, @AuthenticationPrincipal UserDetails userDetails) {
        String loginId = request.get("loginId");
        String fromLoginId = userDetails.getUsername();
        likeService.unlikeUser(loginId, fromLoginId);
        System.out.println("Unlike request processed for user: " + loginId + " by: " + fromLoginId);
    }
}
