package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.AdminLikeRankingService;
import com.example.demo.service.UserLikeCountService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final AdminLikeRankingService rankingService;
    private final UserLikeCountService userLikeCountService;

    @GetMapping("/admindashboard")
    public String adminDashboard(Model model) {
        // 年間ランキングと月間ランキングをモデルに追加
        model.addAttribute("annualRanking", rankingService.getTop5UsersForYear());
        model.addAttribute("monthlyRanking", rankingService.getTop5UsersForMonth());
        
        return "admindashboard";
    }

    @GetMapping("/userdashboard")
    public String userDashboard(Model model) {
        // 年間と月間のいいね獲得数を取得してモデルに追加
        long annualLikeCount = userLikeCountService.getAnnualLikeCount();
        long monthlyLikeCount = userLikeCountService.getMonthlyLikeCount();

        model.addAttribute("annualLikeCount", annualLikeCount);
        model.addAttribute("monthlyLikeCount", monthlyLikeCount);

        return "userdashboard";
    }
}