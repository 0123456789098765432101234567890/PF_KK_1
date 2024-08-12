package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.AdminLikeRankingService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final AdminLikeRankingService rankingService;

    @GetMapping("/admindashboard")
    public String adminDashboard(Model model) {
        // 年間ランキングと月間ランキングをモデルに追加
        model.addAttribute("annualRanking", rankingService.getTop5UsersForYear());
        model.addAttribute("monthlyRanking", rankingService.getTop5UsersForMonth());
        
        return "admindashboard";
    }

    @GetMapping("/userdashboard")
    public String userDashboard() {
        return "userdashboard";
    }
}
