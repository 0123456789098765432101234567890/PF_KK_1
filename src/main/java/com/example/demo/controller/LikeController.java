package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> likeUser(@RequestBody LikeRequest likeRequest) {
        likeService.likeUser(likeRequest.getLoginId(), likeRequest.getFromLoginId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unlike")
    public ResponseEntity<Void> unlikeUser(@RequestBody LikeRequest likeRequest) {
        likeService.unlikeUser(likeRequest.getLoginId(), likeRequest.getFromLoginId());
        return ResponseEntity.ok().build();
    }

    public static class LikeRequest {
        private String loginId;
        private String fromLoginId;

        // Getters and setters

        public String getLoginId() {
            return loginId;
        }

        public void setLoginId(String loginId) {
            this.loginId = loginId;
        }

        public String getFromLoginId() {
            return fromLoginId;
        }

        public void setFromLoginId(String fromLoginId) {
            this.fromLoginId = fromLoginId;
        }
    }
}
