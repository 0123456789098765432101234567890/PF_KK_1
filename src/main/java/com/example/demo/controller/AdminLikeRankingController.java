package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.AdminLikeRankingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//管理者権限用いいねランキング

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminLikeRankingController {

    private final AdminLikeRankingService rankingService;

    /**
     * /adminLikeRanking にアクセスした際に年間と月間のランキングを表示
     * @param model モデルにランキングデータを追加
     * @return adminLikeRanking テンプレート
     */
    @GetMapping("/adminLikeRanking")
    public String getAdminLikeRanking(Model model) {
        // 年間ランキングと月間ランキングをモデルに追加
        model.addAttribute("annualRanking", rankingService.getTop5UsersForYear());
        model.addAttribute("monthlyRanking", rankingService.getTop5UsersForMonth());
        
        // adminLikeRanking テンプレートを表示
        return "adminLikeRanking";  // HTMLファイル名が `adminLikeRanking.html` であると仮定しています
    }
}
