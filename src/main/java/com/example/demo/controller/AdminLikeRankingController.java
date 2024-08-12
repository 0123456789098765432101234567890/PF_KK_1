package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.AdminLikeRankingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminLikeRankingController {

    private final AdminLikeRankingService rankingService;

    @GetMapping("/adminLikeRanking")
    public String showAdminLikeRanking(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String loginId = userDetails.getUsername();
        log.info("AdminLikeRankingページを表示しています。ログインID: {}", loginId);

        try {
            List<UserInfo> monthlyRanking = rankingService.getTop5UsersForMonth();
            List<UserInfo> weeklyRanking = rankingService.getTop5UsersForWeek();
            
            // デバッグのために各ユーザーの画像データをログに出力
            monthlyRanking.forEach(user -> log.info("User: {}, ImageData: {}", user.getLoginId(), user.getBase64Image()));
            weeklyRanking.forEach(user -> log.info("User: {}, ImageData: {}", user.getLoginId(), user.getBase64Image()));

            model.addAttribute("monthlyRanking", monthlyRanking);
            model.addAttribute("weeklyRanking", weeklyRanking);

            return "adminLikeRanking";
        } catch (Exception e) {
            log.error("ランキングの取得中にエラーが発生しました", e);
            return "error/500";
        }
    }
}
