package com.example.demo.controller;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.LikeRankingPublicService;
import com.example.demo.service.LikeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LikeRankingPublicController {

    private final LikeRankingPublicService likeRankingPublicService;
    private final LikeService likeService;

    @GetMapping("/likeranking")
    public String getLikeRanking(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String loginId = userDetails.getUsername(); // ログイン中のユーザーIDを取得
        List<UserInfo> users = likeRankingPublicService.getMonthlyRanking(); // 月間ランキングを取得

        for (UserInfo user : users) {
            boolean liked = likeService.hasLiked(user.getLoginId(), loginId); // 各ユーザーがいいねされているか確認
            user.setLiked(liked); // likedフラグを設定

            // 月間のいいねカウントを取得してセット
            YearMonth currentMonth = YearMonth.now();
            LocalDateTime startOfMonth = currentMonth.atDay(1).atStartOfDay();
            LocalDateTime endOfMonth = currentMonth.atEndOfMonth().atTime(23, 59, 59);
            long monthlyLikeCount = likeRankingPublicService.countMonthlyLikesByLoginId(user.getLoginId(), startOfMonth, endOfMonth);
            user.setLikeCount(monthlyLikeCount); // 月間のいいね数を設定
        }

        model.addAttribute("users", users); // ユーザー情報をモデルに追加
        return "likeRankingPublicList"; // likeRankingPublicList.htmlを表示
    }
}
