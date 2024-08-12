package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.UserLikeCountService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserLikeCountController {

    private final UserLikeCountService likeCountService;

    /**
     * ログイン中のユーザーの年間と月間のいいね数を表示する
     * @param model モデルにいいね数を追加
     * @return userLikeCount テンプレート
     */
    @GetMapping("/userLikeCount")
    public String getUserLikeCount(Model model) {
        long annualLikeCount = likeCountService.getAnnualLikeCount();
        long monthlyLikeCount = likeCountService.getMonthlyLikeCount();

        // いいね数をモデルに追加
        model.addAttribute("annualLikeCount", annualLikeCount);
        model.addAttribute("monthlyLikeCount", monthlyLikeCount);

        log.info("年間いいね数: {}, 月間いいね数: {}", annualLikeCount, monthlyLikeCount);

        return "userLikeCount";  // HTMLファイル名が `userLikeCount.html` であると仮定しています
    }
}
