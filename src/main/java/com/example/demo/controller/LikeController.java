package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<Map<String, Object>> likeUser(@RequestBody Map<String, String> payload, @AuthenticationPrincipal UserDetails userDetails) {
        String loginId = payload.get("loginId");
        String fromLoginId = userDetails.getUsername();
        likeService.likeUser(loginId, fromLoginId);
        long likeCount = likeService.countLikesByLoginId(loginId);

        Map<String, Object> response = new HashMap<>();
        response.put("likeCount", likeCount);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/unlike")
    public ResponseEntity<Map<String, Object>> unlikeUser(@RequestBody Map<String, String> payload, @AuthenticationPrincipal UserDetails userDetails) {
        String loginId = payload.get("loginId");
        String fromLoginId = userDetails.getUsername();
        likeService.unlikeUser(loginId, fromLoginId);
        long likeCount = likeService.countLikesByLoginId(loginId);

        Map<String, Object> response = new HashMap<>();
        response.put("likeCount", likeCount);

        return ResponseEntity.ok(response);
    }
}
