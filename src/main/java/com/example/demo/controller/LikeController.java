package com.example.demo.controller;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.LikeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/like")
    public ResponseEntity<Map<String, Object>> likeUser(@RequestBody Map<String, String> payload, 
                                                        @RequestHeader(value = "X-Request-Source", required = false) String requestSource) {
        String loginId = payload.get("loginId");
        String fromLoginId = SecurityContextHolder.getContext().getAuthentication().getName();

        likeService.likeUser(loginId, fromLoginId);

        long likeCount;

        // リクエストが「月間ランキング」画面からの場合のみ月間の「いいね」数を返す
        if ("monthlyRanking".equals(requestSource)) {
            LocalDateTime startOfMonth = YearMonth.now().atDay(1).atStartOfDay();
            LocalDateTime endOfMonth = YearMonth.now().atEndOfMonth().atTime(23, 59, 59);
            likeCount = likeService.countLikesByLoginIdAndDateRange(loginId, startOfMonth, endOfMonth);
        } else {
            // 他の画面では全期間の「いいね」数を返す
            likeCount = likeService.countLikesByLoginId(loginId);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("likeCount", likeCount);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/unlike")
    public ResponseEntity<Map<String, Object>> unlikeUser(@RequestBody Map<String, String> payload, 
                                                          @AuthenticationPrincipal UserDetails userDetails,
                                                          @RequestHeader(value = "X-Request-Source", required = false) String requestSource) {
        String loginId = payload.get("loginId");
        String fromLoginId = userDetails.getUsername();

        likeService.unlikeUser(loginId, fromLoginId);

        long likeCount;

        // リクエストが「月間ランキング」画面からの場合のみ月間の「いいね」数を返す
        if ("monthlyRanking".equals(requestSource)) {
            LocalDateTime startOfMonth = YearMonth.now().atDay(1).atStartOfDay();
            LocalDateTime endOfMonth = YearMonth.now().atEndOfMonth().atTime(23, 59, 59);
            likeCount = likeService.countLikesByLoginIdAndDateRange(loginId, startOfMonth, endOfMonth);
        } else {
            // 他の画面では全期間の「いいね」数を返す
            likeCount = likeService.countLikesByLoginId(loginId);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("likeCount", likeCount);

        return ResponseEntity.ok(response);
    }
}
