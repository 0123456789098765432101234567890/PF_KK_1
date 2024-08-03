package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.LikeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // Slf4jアノテーションを追加

@RestController
@RequiredArgsConstructor
@Slf4j // Slf4jアノテーションでログを有効にする
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/like")
    public ResponseEntity<Map<String, Long>> likeUser(@RequestBody Map<String, String> payload) {
        String loginId = payload.get("loginId");
        String fromLoginId = SecurityContextHolder.getContext().getAuthentication().getName(); // 現在ログイン中のユーザーID

        // ログ出力を追加して、どのユーザーがどのユーザーをいいねしたかを確認
        log.debug("Received like request from {} to {}", fromLoginId, loginId);

        likeService.likeUser(loginId, fromLoginId);

        // 更新されたいいね数を取得
        long likeCount = likeService.countLikesByLoginId(loginId);
        Map<String, Long> response = new HashMap<>();
        response.put("likeCount", likeCount);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/unlike")
    public ResponseEntity<Map<String, Object>> unlikeUser(@RequestBody Map<String, String> payload, @AuthenticationPrincipal UserDetails userDetails) {
        String loginId = payload.get("loginId");
        String fromLoginId = userDetails.getUsername();

        // ログ出力を追加して、どのユーザーがどのユーザーをいいね解除したかを確認
        log.debug("Received unlike request from {} to {}", fromLoginId, loginId);

        likeService.unlikeUser(loginId, fromLoginId);
        long likeCount = likeService.countLikesByLoginId(loginId);

        Map<String, Object> response = new HashMap<>();
        response.put("likeCount", likeCount);

        return ResponseEntity.ok(response);
    }
}
